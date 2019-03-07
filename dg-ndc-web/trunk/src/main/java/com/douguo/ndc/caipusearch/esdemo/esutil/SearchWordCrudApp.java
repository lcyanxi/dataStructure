package com.douguo.ndc.caipusearch.esdemo.esutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douguo.ndc.caipusearch.routchoose.dao.DgCookimginfoDao;
import com.douguo.ndc.caipusearch.util.SortByLengthComparator;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/10
 */
@Component public class SearchWordCrudApp {

    private static final String CATEGORIES = "categories";
    private static final int SIZE = 50;

    @Autowired private DgCookimginfoDao dgCookimginfoDao;

    /**
     * 执行关键词搜索
     *
     * @param client     es客户端
     * @param searchWord 搜索关键词
     * @param excludeKey 排除词
     * @return data
     */
    public JSONObject executeSearch(TransportClient client, String searchWord, String excludeKey,
        StringBuffer accessRout) {

        QueryBuilder queryBuilder;
        QueryBuilder qb = QueryBuilders.matchPhraseQuery("cook_name", searchWord);

        if (StringUtils.isNotBlank(excludeKey)) {
            String[] excludekeys = excludeKey.split(",");
            QueryBuilder qb1 = QueryBuilders.termsQuery("cook_name", excludekeys);
            queryBuilder = QueryBuilders.boolQuery().must(qb).mustNot(qb1);
        } else {
            queryBuilder = QueryBuilders.boolQuery().must(qb);

        }
        //对于keyword索引类型排序方式为Tscore(rank)*Zscore

        ScoreFunctionBuilder scoreFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("rank");
        FunctionScoreQueryBuilder query =
            QueryBuilders.functionScoreQuery(queryBuilder, scoreFunctionBuilder).boostMode(CombineFunction.MULT);

        SearchResponse response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(query)
            .setFetchSource(
                new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                    "collects_num", "cook_level", "rank", "source"}, null).setSize(SIZE).get();

        JSONObject jsonObject = executeSearchUtil(response);
        //如果不够50条 将搜索词按ik_smart分词器进行分词  分词后按长度最长的进行搜索  最终拿到50条数据

        List<String> tmpWord = new ArrayList<>();
        Long num = (Long)jsonObject.get("total");
        int total = num.intValue();
        accessRout.append("->搜索词结果数量" + total);
        //获取分词后的词语
        if (total < SIZE) {
            accessRout.append("->搜索词不够进行分词");
            AnalyzeRequest analyzeRequest = new AnalyzeRequest("recipe_index").text(searchWord).analyzer("ik_smart");
            List<AnalyzeResponse.AnalyzeToken> tokens =
                client.admin().indices().analyze(analyzeRequest).actionGet().getTokens();
            for (AnalyzeResponse.AnalyzeToken token : tokens) {
                tmpWord.add(token.getTerm());
            }
            // 搜索词分词后长度排序
            Collections.sort(tmpWord, new SortByLengthComparator());
            accessRout.append("->分词结果:" + tmpWord);
            //再用分词后的词语查询
            for (String word : tmpWord) {
                JSONObject exctJson = executeSearchWordUtils(client, word, SIZE - total);
                Long tmpNum = (Long)exctJson.get("total");
                List list = (List)jsonObject.get("data");
                list.addAll((List)exctJson.get("data"));
                jsonObject.put("data", list);
                total = total + tmpNum.intValue();
                if (total > SIZE) {
                    break;
                }
            }
        }
        jsonObject.put("total", total);
        return jsonObject;
    }

    /**
     * 执行关键词搜索新逻辑
     *
     * @param client     es客户端
     * @param searchWord 搜索关键词
     * @return data
     */
    public JSONObject executeSearchNew(TransportClient client, String searchWord, StringBuffer accessRout) {
        int total = 0;
        QueryBuilder query = QueryBuilders.termQuery("cook_name", searchWord);

        SearchResponse response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(query)
            .setFetchSource(
                new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                    "collects_num", "cook_level", "rank", "source"}, null).setSize(SIZE).get();
        JSONObject result = executeSearchNew(client, accessRout, response, total);

        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet<Map<String, Object>> list = (LinkedHashSet)result.get("data");

        for (Map<String, Object> map : list) {
            linkedHashSet.add(map.get("cook_id"));
        }
        total = result.getIntValue("total");

        if (total < SIZE) {
            accessRout.append("->term搜索不够");
            query = QueryBuilders.matchPhraseQuery("cook_name", searchWord);
             response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(query)
                .setFetchSource(
                    new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                        "collects_num", "cook_level", "rank", "source"}, null).setSize(SIZE-total).get();
            JSONObject target = executeSearchNew(client, accessRout, response, total);
            total = target.getIntValue("total");
            convet(target, result,linkedHashSet);
            if (total < SIZE) {
                query = QueryBuilders.matchQuery("cook_name", searchWord).operator(MatchQueryBuilder.Operator.AND);
                accessRout.append("->matchPhrase搜索不够");
                 response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(query)
                    .setFetchSource(
                        new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                            "collects_num", "cook_level", "rank", "source"}, null).setSize(SIZE-total).get();
                JSONObject target2 = executeSearchNew(client, accessRout, response, total);
                convet(target2, result,linkedHashSet);
                total = target2.getIntValue("total");

                if (total < SIZE) {
                    query = QueryBuilders.matchQuery("cook_name", searchWord);
                  response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(query)
                        .setFetchSource(
                            new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                                "collects_num", "cook_level", "rank", "source"}, null).setSize(SIZE-total).get();
                    accessRout.append("->matchAnd搜索不够");
                    JSONObject target3 = executeSearchNew(client, accessRout, response, total);
                    convet(target3, result,linkedHashSet);

                }
            }
        }
        result.put("total", total);
        return result;
    }

    public JSONObject executeSearchNew(TransportClient client, StringBuffer accessRout,SearchResponse response, int total) {
        JSONObject jsonObject = new JSONObject();
        executeSearchUtil(response, jsonObject);
        total = total + jsonObject.getIntValue("total");
        accessRout.append("->搜索词结果数量" + total);

        jsonObject.put("total", total);

        return jsonObject;
    }

    /**
     * 执行标签和食材搜索
     *
     * @param client     es客户端
     * @param searchType 搜索类型
     * @param searchWord 搜索词
     * @return data
     */
    public JSONObject executeSearch(TransportClient client, String searchType, String searchWord) {
        QueryBuilder queryBuilder;
        //执行标签搜索
        if (searchType.equals(CATEGORIES)) {
            queryBuilder = QueryBuilders.termQuery("categories.name", searchWord);
            //执行食材搜索
        } else {
            queryBuilder = QueryBuilders.termQuery("ingredients.name", searchWord);
            //执行关键词搜索
        }

        SearchResponse response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(queryBuilder)
            .setFetchSource(
                new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                    "collects_num", "cook_level", "rank", "source"}, null).addSort("rank", SortOrder.DESC).setSize(SIZE)
            .get();

        return executeSearchUtil(response);
    }

    /**
     * 关键词搜索辅助类
     *
     * @param client     es客户端
     * @param searchWord 搜索关键词
     * @param size       结果数据大小
     * @return data
     */
    public JSONObject executeSearchWordUtils(TransportClient client, String searchWord, int size) {

        QueryBuilder queryBuilder = QueryBuilders.termQuery("cook_name", searchWord);

        SearchResponse response = client.prepareSearch("recipe_index").setTypes("recipe").setQuery(queryBuilder)
            .setFetchSource(
                new String[] {"cook_id", "user_id", "cook_name", "std_name", "ingredients", "categories", "dishes_num",
                    "collects_num", "cook_level", "rank", "source"}, null).setSize(size).get();
        return executeSearchUtil(response);
    }

    private JSONObject executeSearchUtil(SearchResponse response) {
        JSONObject result = new JSONObject();
        SearchHit[] searchHits = response.getHits().getHits();
        LinkedHashSet list = new LinkedHashSet();
        long total = response.getHits().getTotalHits();
        for (int i = 0; i < searchHits.length; i++) {
            JSONObject jsonObject = JSONObject.parseObject(searchHits[i].getSourceAsString());
            jsonObject.put("score", searchHits[i].getScore());

            if (jsonObject.isEmpty()) {
                continue;
            }
            list.add(jsonObject);
            // 获取图片信息
            List<Map<String, Object>> imgeList = dgCookimginfoDao.queryDgCookimginfo(jsonObject.getString("cook_id"));
            if (!imgeList.isEmpty()) {
                Map tempMap = imgeList.get(0);
                jsonObject.put("cook_image", tempMap.get("cook_image"));
                jsonObject.put("width", tempMap.get("width"));
                jsonObject.put("height", tempMap.get("height"));
            }
        }
        result.put("data", list);
        result.put("total", total);
        return result;
    }

    private JSONObject executeSearchUtil(SearchResponse response, JSONObject result) {
        SearchHit[] searchHits = response.getHits().getHits();
        LinkedHashSet list = new LinkedHashSet();
        long total = response.getHits().getTotalHits();
        for (int i = 0; i < searchHits.length; i++) {
            JSONObject jsonObject = JSONObject.parseObject(searchHits[i].getSourceAsString());
            jsonObject.put("score", searchHits[i].getScore());

            if (jsonObject.isEmpty()) {
                continue;
            }
            list.add(jsonObject);
            // 获取图片信息
            List<Map<String, Object>> imgeList = dgCookimginfoDao.queryDgCookimginfo(jsonObject.getString("cook_id"));
            if (!imgeList.isEmpty()) {
                Map tempMap = imgeList.get(0);
                jsonObject.put("cook_image", tempMap.get("cook_image"));
                jsonObject.put("width", tempMap.get("width"));
                jsonObject.put("height", tempMap.get("height"));
            }
        }
        result.put("data", list);
        result.put("total", searchHits.length);
        return result;
    }

    private void convet(JSONObject target, JSONObject result,LinkedHashSet hashSet) {
        LinkedHashSet<Map<String,Object>> list = (LinkedHashSet)target.get("data");
        LinkedHashSet<Map<String,Object>> list1 = (LinkedHashSet)result.get("data");

        for (Map<String,Object> map : list) {
            Object cook_id=map.get("cook_id");
            if (hashSet.contains(cook_id)){
                continue;

            }
            hashSet.add(cook_id);
           list1.add(map);
        }

        result.put("data", list1);
    }
}
