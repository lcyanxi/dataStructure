package com.douguo.ndc.web;

import com.douguo.ndc.service.MenuService;
import com.douguo.ndc.util.ConstantStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcyanxi on 2018/7/27.
 */
@Controller @RequestMapping("/index") public class WebController extends CommonController {

    @Autowired private MenuService menuService;

    @RequestMapping(value = "/home", method = RequestMethod.GET) public String hello() {
        return "pages/home";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET) public String toIndex() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET) public String toLogin() {
        return "pages/login";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET) public String to403() {
        return "pages/403";
    }

    @RequestMapping(value = "/permission/{uid}", method = RequestMethod.GET)
    public String toPermission(@PathVariable(value = "uid") String uid, Model model) {
        model.addAttribute("uid", uid);
        return "pages/permission";
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET) public String toTree() {
        return "pages/tree";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET) public String toUserProfile() {
        return "pages/user_profile";
    }

    @RequestMapping(value = "/queryMenu", method = RequestMethod.GET) @ResponseBody public String queryMenus() {
        String uid = (String)session.getAttribute(ConstantStr.UID);
        String str = (String)session.getAttribute(ConstantStr.STRMENU);
        if (str == null) {
            str = menuService.queryMenus(uid);
            //如果是开发者就不用缓存左侧菜单数据了
            if (!uid.equals(ConstantStr.LICHANG)) {
                session.setAttribute(ConstantStr.STRMENU, str);
            }

        }
        return str;
    }

    @RequestMapping(value = "/queryMenuPermission", method = RequestMethod.GET) @ResponseBody
    public String queryMenusPermission(String uid) {
        return menuService.queryMenusPermission(uid);
    }

    @RequestMapping(value = "/updateMenuPermission/{uid}", method = RequestMethod.GET) @ResponseBody
    public Map updateMenusPermission(String nodes, @PathVariable(value = "uid") String uid) {

        Map map = new HashMap(16);

        menuService.updateMenusPermission(nodes, uid);

        map.put(ConstantStr.MESSAGE, "授权成功");
        return map;
    }

}
