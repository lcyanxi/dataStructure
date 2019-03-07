package com.douguo.ndc.web;

import com.douguo.ndc.service.DouguoExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/2
 */
@Controller @RequestMapping("/douguoExt") public class DouguoExtController {
    @Autowired private DouguoExtService douguoExtService;

    @RequestMapping(value = "/to_page", method = RequestMethod.GET) public String toPage() {
        return "pages/douguo_ext";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET) @ResponseBody public Map queryDouguoExt(String type) {
        return douguoExtService.queryDouguoExt(type);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET) @ResponseBody
    public Map updateDouguoExt(int id, int flag,String type) {
        return douguoExtService.updateDouguoExt(id, flag,type);
    }
}
