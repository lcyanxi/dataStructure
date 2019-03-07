package com.douguo.ndc.service;

import com.douguo.ndc.dao.UserDao;
import com.douguo.ndc.model.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lcyanxi on 2018/8/12.
 */
@Service public class UserService {

    @Autowired private UserDao userDao;

    public User selectByIdentify(String username) {

        User user = null;
        List<User> list = userDao.getUserByUid(username);
        if (!list.isEmpty()) {
            user = list.get(0);
        }
        return user;
    }

    public List<User> selectAllUser() {
       return userDao.getUsers();
    }
    public List<String> selectMenus(String uid) {
        List<String> list = new ArrayList<>();
        List<Map<String, Object>> target = userDao.getMenuLists(uid);
        for (Map<String, Object> map : target) {

            list.add((String)map.get("url"));
        }
        return list;
    }

    public Set<String> queryPermission(){
        Set<String> list = new HashSet<>();
        List<Map<String, Object>> target = userDao.queryPermission();
        for (Map<String, Object> map : target) {
            list.add((String)map.get("url"));
        }
        return list;
    }

    public static void main(String args[]) {
        Md5Hash md5Hash = new Md5Hash("重庆鸡公煲");
        System.out.print(md5Hash.toString());
    }
}
