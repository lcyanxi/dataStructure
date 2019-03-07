package com.douguo.ndc.caipusearch.routchoose.service;

import com.douguo.ndc.caipusearch.routchoose.dao.DgCategoriesDao;
import com.douguo.ndc.caipusearch.routchoose.dao.DgIngredientsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/12/12
 */
@Service public class RoutChooseService {

    @Autowired DgCategoriesDao dgCategoriesDao;
    @Autowired DgIngredientsDao dgIngredientsDao;


    public String querySearchWord(String searchword) {

        //获取标签数据
        for (Map<String, Object> categoriesMap : dgCategoriesDao.queryDgCategories()) {
            String uname = (String)categoriesMap.get("name");
            if (searchword.equals(uname)){
                return "categories";
            }
        }

        //获取标签数据
        for (Map<String, Object> ingredientsMap : dgIngredientsDao.queryDgIngredients()) {
            String uname = (String)ingredientsMap.get("name");
            if (searchword.equals(uname)){
                return "ingredients";
            }
        }
        return "keyword";
    }
}
