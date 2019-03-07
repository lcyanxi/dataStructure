package com.douguo.ndc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.douguo.ndc.dao.MenuDao;

import com.douguo.ndc.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/28
 */
@Service
public class MenuService {
    @Autowired
    private MenuDao menuDao;

    public String  queryMenus(String uid){
        List<Map<String,Object>> list=menuDao.queryMenus(uid);

        JSONArray jsonArray=new JSONArray();
        for (Map<String,Object> map:list){
            JSONObject jsonObject=new JSONObject();
           Integer pid= (Integer)map.get("pid");
            if (pid==0){
                jsonObject.put("icon",map.get("icon"));
            }else {
                jsonObject.put("icon","");
            }
            jsonObject.put("id",map.get("id"));
            jsonObject.put("pId",map.get("pid"));
            jsonObject.put("url",map.get("url"));
            jsonObject.put("hasChild",map.get("has_child"));
            jsonObject.put("name",map.get("name"));
            jsonArray.add(jsonObject);
        }
        String str = JSONObject.toJSONString(jsonArray, SerializerFeature.WriteMapNullValue);
        return str;
    }
    public String  queryMenusPermission(String uid){
        List<Map<String,Object>> list=menuDao.queryMenusPermission(uid);
        return transfUtil(list);
    }

    public boolean  updateMenusPermission(String nodes,String uid){
        String[]  nodesArray = nodes.split(",");
        List list=Arrays.asList(nodesArray);
        return menuDao.updateMenusPermission(list,uid);

    }
    public String  queryMenusTree(){
        List<Map<String,Object>> list=menuDao.queryMenusTree();
        return transfUtil(list);
    }

    public boolean  updateMenuLocation(String id,String pid){
        return menuDao.updateMenuLocation(id,pid);
    }
    public boolean  updateMenuLocation(String ids){

        return menuDao.updateMenuLocation(ids);
    }


    public boolean  addMenuNode(Menu menu){
        return menuDao.addMenuNode(menu);

    }

    public boolean  deleteMenuNode(String id){
        return menuDao.deleteMenuNode(id);

    }
    public boolean  renameMenuNode(String id,String newName){
        return menuDao.renameMenuNode(id,newName);

    }

    private String  transfUtil(List<Map<String,Object>> list){
        JSONArray jsonArray=new JSONArray();
        for (Map<String,Object> map:list){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",map.get("id"));
            jsonObject.put("pId",map.get("pid"));
            jsonObject.put("hasChild",map.get("has_child"));
            jsonObject.put("name",map.get("name"));
            jsonObject.put("sortId",map.get("sortId"));
            jsonObject.put("open",true);
            jsonArray.add(jsonObject);
        }
        String str = JSONObject.toJSONString(jsonArray, SerializerFeature.WriteMapNullValue);
        return str;
    }
}
