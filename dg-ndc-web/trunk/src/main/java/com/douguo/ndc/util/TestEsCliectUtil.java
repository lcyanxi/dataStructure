package com.douguo.ndc.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/17
 */
@Component public class TestEsCliectUtil {

    private static final Logger log = LoggerFactory.getLogger(TestEsCliectUtil.class);
    private static final String USER_PROFILE_INDEX = "es_user_profile_a";
    private static final String ZERO = "0";
    private static final String SIGIN="0";
    private static final String CROWD = "crowd.name";
    private static final String All = "all";

    @Resource(name = "esClient") private TransportClient client;

    /**
     * 获取es json数据
     *
     * @param crowd       人群
     * @param strTagName  一级标签
     * @param strTagValue 二级标签
     * @param strTagSub   九个分类标签的其中一个
     * @param size        数据大小
     * @param startDate   开始日期
     * @param endDate     结束日期
     * @param isLogin     是否是注册用户
     * @return
     */
    //  @Cacheable(key = "#crowd+'_'+#strTagName+'_'+#strTagValue+'_'+#strTagSub+'_'+#startDate+'_'+#endDate+'_'+#isLogin", value = RedisConstantStr.PROFILE_GETRECOMMENDCOOKS)
    public JSONObject getRecommendCooks(String crowd, String strTagName, String strTagValue, String strTagSub,
        Integer size, String startDate, String endDate, String isLogin,String flag) {

        long begin = System.currentTimeMillis();
        log.info(" crowd :" + crowd);
        log.info(" tag_name :" + strTagName);
        log.info(" tag_value:" + strTagValue);
        log.info(" tag_sub:" + strTagSub);
        log.info("begin_date:" + startDate);
        log.info("end_date:" + endDate);

        // 返回的json数据
        JSONObject jsonObj = new JSONObject();

        // 1级标签数据
        List<Map<String, Long>> listTag = new ArrayList<>();

        // 子级标签数据
        List<Map<String, List<Map<String, Long>>>> listTagSub = new ArrayList<>();
        long end = System.currentTimeMillis();
        log.info("times1:" + (end - begin));
        // ES 统计

        SearchRequestBuilder sbuilder = client.prepareSearch(USER_PROFILE_INDEX);

        end = System.currentTimeMillis();
        log.info("times2:" + (end - begin));
        // 相当于 select count_tag from table group by strTagName
        TermsBuilder agg1 = AggregationBuilders.terms("count_tag").field(strTagName).size(size);

        end = System.currentTimeMillis();
        log.info("times3:" + (end - begin));
        // 过滤

        QueryBuilder qb;
        QueryBuilder qbCrowd = QueryBuilders.boolQuery();
        QueryBuilder qbStrTagValue = QueryBuilders.boolQuery();
        QueryBuilder qbIsLogin = QueryBuilders.boolQuery();
        QueryBuilder qbLastTime=QueryBuilders.rangeQuery("last_login_time").from(startDate).to(endDate);
        QueryBuilder qbexit = QueryBuilders.boolQuery();

        if (!flag.equals(SIGIN)){
            qbexit=QueryBuilders.existsQuery(flag);
        }
        if (isLogin.equals(ZERO)) {
            isLogin = "";
        }
        log.info("isLogin:" + isLogin);

        if (!All.equals(crowd)) {
            qbCrowd = QueryBuilders.termsQuery(CROWD, crowd);
        }
        if (!All.equals(strTagValue)) {
            qbStrTagValue = QueryBuilders.termsQuery(strTagName, strTagValue);
        }
        if (StringUtils.isNotBlank(isLogin)) {
            qbIsLogin = QueryBuilders.termsQuery("is_login", isLogin);
        }

        qb = QueryBuilders.boolQuery().must(qbCrowd).must(qbStrTagValue).must(qbIsLogin).must(qbLastTime).must(qbexit);
        end = System.currentTimeMillis();
        log.info("times4:" + (end - begin));
        // 构建

        if (StringUtils.isNotBlank(strTagSub)) {
            TermsBuilder agg2 = AggregationBuilders.terms("count_tag_sub").field(strTagSub).size(size);
            sbuilder.setQuery(qb).addAggregation(agg1.subAggregation(agg2));
        } else {
            sbuilder.setQuery(qb).addAggregation(agg1);
        }

        end = System.currentTimeMillis();
        log.info("times5:" + (end - begin));

        // 执行查询
        SearchResponse response = sbuilder.execute().actionGet();
        end = System.currentTimeMillis();
        log.info("times6:" + (end - begin));
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        StringTerms tagAgg = (StringTerms)aggMap.get("count_tag");
        Iterator<Terms.Bucket> tagBucketIt = tagAgg.getBuckets().iterator();
        end = System.currentTimeMillis();
        log.info("times7:" + (end - begin));
        // 构建一级标签聚合数据
        while (tagBucketIt.hasNext()) {
            Terms.Bucket bucket = tagBucketIt.next();
            // term
            Map<String, Long> map = new HashMap<>(16);
            String term = bucket.getKeyAsString();
            long count = bucket.getDocCount();
            map.put(term, count);
            listTag.add(map);

            // 构建子标签聚合数据
            List<Map<String, Long>> listSub = new ArrayList<>();

            if (StringUtils.isNotBlank(strTagSub)) {
                // 得到子集合数据
                Map<String, Aggregation> subAggMap = bucket.getAggregations().asMap();
                //
                StringTerms subTermAgg = (StringTerms)subAggMap.get("count_tag_sub");
                Iterator<Terms.Bucket> subTagBucketIt = subTermAgg.getBuckets().iterator();
                Map<String, List<Map<String, Long>>> mapTagSub = new HashMap<>(16);
                while (subTagBucketIt.hasNext()) {
                    Terms.Bucket bucketSub = subTagBucketIt.next();
                    // term
                    String termSub = bucketSub.getKeyAsString();
                    long countSub = bucketSub.getDocCount();

                    Map<String, Long> mapSub = new HashMap<>(16);
                    mapSub.put(termSub, countSub);

                    listSub.add(mapSub);
                }

                mapTagSub.put(term, listSub);

                listTagSub.add(mapTagSub);
            } // end if
        } // end while

        // 一级标签数据
        jsonObj.put("list_tag", listTag);
        // 子标签数据
        jsonObj.put("list_tag_sub", listTagSub);

        end = System.currentTimeMillis();
        log.info("times8:" + (end - begin));
        return jsonObj;
    }


    public JSONObject getRecommendCooks(String crowd, String strTagSub, Integer size, String startDate, String endDate,
        String isLogin,String flag) {

        long begin = System.currentTimeMillis();
        log.info(" crowd :" + crowd);
        log.info(" tag_sub:" + strTagSub);
        log.info("begin_date:" + startDate);
        log.info("end_date:" + endDate);

        // 返回的json数据
        JSONObject jsonObj = new JSONObject();

        // 1级标签数据
        List<Map<String, Long>> listTag = new ArrayList<>();
        long end = System.currentTimeMillis();
        log.info("times1:" + (end - begin));
        // ES 统计

        SearchRequestBuilder sbuilder = client.prepareSearch(USER_PROFILE_INDEX);

        end = System.currentTimeMillis();
        log.info("times2:" + (end - begin));
        // 相当于 select count_tag from table group by strTagName
        TermsBuilder agg1 = AggregationBuilders.terms("count_tag").field(strTagSub).size(size);

        end = System.currentTimeMillis();
        log.info("times3:" + (end - begin));
        // 过滤

        QueryBuilder qb;
        QueryBuilder qbCrowd = QueryBuilders.boolQuery();
        QueryBuilder qbIsLogin = QueryBuilders.boolQuery();
        QueryBuilder qbLastTime=QueryBuilders.rangeQuery("last_login_time").from(startDate).to(endDate);


        if (isLogin.equals(ZERO)) {
            isLogin = "";
        }
        log.info("isLogin:" + isLogin);

        if (!All.equals(crowd)) {
            qbCrowd = QueryBuilders.termsQuery(CROWD, crowd);
        }
        if (StringUtils.isNotBlank(isLogin)) {
            qbIsLogin = QueryBuilders.termsQuery("is_login", isLogin);
        }

        if (flag.equals(SIGIN)){
            qb = QueryBuilders.boolQuery().must(qbCrowd).must(qbIsLogin).must(qbLastTime);
        }else {
            qb = QueryBuilders.boolQuery().must(qbCrowd).must(qbIsLogin).must(qbLastTime)
                .must(QueryBuilders.existsQuery(flag));
        }
        end = System.currentTimeMillis();
        log.info("times4:" + (end - begin));
        // 构建

        sbuilder.setQuery(qb).addAggregation(agg1);

        end = System.currentTimeMillis();
        log.info("times5:" + (end - begin));

        // 执行查询
        SearchResponse response = sbuilder.execute().actionGet();
        end = System.currentTimeMillis();
        log.info("times6:" + (end - begin));
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        StringTerms tagAgg = (StringTerms)aggMap.get("count_tag");
        Iterator<Terms.Bucket> tagBucketIt = tagAgg.getBuckets().iterator();
        end = System.currentTimeMillis();
        log.info("times7:" + (end - begin));
        // 构建一级标签聚合数据
        while (tagBucketIt.hasNext()) {
            Terms.Bucket bucket = tagBucketIt.next();
            // term
            Map<String, Long> map = new HashMap<>(16);
            String term = bucket.getKeyAsString();
            long count = bucket.getDocCount();
            map.put(term, count);
            listTag.add(map);
        } // end while

        // 一级标签数据
        jsonObj.put("list_tag", listTag);
        end = System.currentTimeMillis();
        log.info("times8:" + (end - begin));
        return jsonObj;
    }

    /**
     * 通过一级标签动态获取二级标签列表
     *
     * @param oneTags 一级标签
     * @return 数据
     */
    public JSONObject getNextTags(String oneTags, int size) {

        // 返回的json数据
        JSONObject jsonObj = new JSONObject();

        // 1级标签数据
        List<Map<String, Long>> listTag = new ArrayList<>();

        SearchRequestBuilder sbuilder = client.prepareSearch(USER_PROFILE_INDEX);
        TermsBuilder agg1 = AggregationBuilders.terms("count_tag").field(oneTags).size(size);
        sbuilder.addAggregation(agg1);
        // 执行查询
        SearchResponse response = sbuilder.execute().actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        StringTerms tagAgg = (StringTerms)aggMap.get("count_tag");
        Iterator<Terms.Bucket> tagBucketIt = tagAgg.getBuckets().iterator();
        // 构建一级标签聚合数据
        while (tagBucketIt.hasNext()) {
            Terms.Bucket bucket = tagBucketIt.next();
            // term
            Map<String, Long> map = new HashMap<>(16);
            String term = bucket.getKeyAsString();
            long count = bucket.getDocCount();
            map.put(term, count);
            listTag.add(map);
        } // end while

        // 一级标签数据
        jsonObj.put("list_tag", listTag);

        return jsonObj;

    }

}
