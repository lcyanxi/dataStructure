package com.douguo.ndc.service;

import com.douguo.ndc.dao.DouguoExtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/2
 */
@Service("douguoExtService ") public class DouguoExtService {

    @Autowired private DouguoExtDao douguoExtDao;

    public Map queryDouguoExt(String type) {
        List<Map<String, Object>> list = douguoExtDao.queryDouguoExt(type);
        Map map = utils(list);
        map.put("total", douguoExtDao.queryCount(type));
        return map;
    }

    public Map updateDouguoExt(int id, int flag, String type) {
        List<Map<String, Object>> list = douguoExtDao.updateDouguoExt(id, flag, type);
        Map map = utils(list);
        map.put("total", douguoExtDao.queryCount(type));
        return map;
    }

    private Map utils(List<Map<String, Object>> list) {
        Map result = new HashMap(16);
        if (list.isEmpty()) {
            result.put("status", 0);
            result.put("message", "已经没有可为你展示的数据了哟");
        } else {
            result.put("status", 1);
        }
        String str = "";
        Long id = 0L;
        for (Map map : list) {
            str = (String)map.get("term");
            id = (Long)map.get("id");
        }

        result.put("keyword", str);
        result.put("id", id);
        return result;
    }
}
