package com.douguo.ndc.web;

import com.douguo.ndc.model.Menu;
import com.douguo.ndc.service.MenuService;
import com.douguo.ndc.util.ConstantStr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/30
 */
@Controller @RequestMapping("/menu") public class MenuController {

    @Autowired private MenuService menuService;

    @RequestMapping(value = "/queryMenuTree", method = RequestMethod.GET) @ResponseBody public String queryMenuTree() {
        return menuService.queryMenusTree();
    }

    /**
     *  调整节点的位置
     * @param ids  目标节点同级的要待排的顺序（已经是有序）
     * @param id  待排节点
     * @param pid 目标节点的pId
     * @return 结果消息
     */
    @RequestMapping(value = "/updatePrevLocation", method = RequestMethod.GET) @ResponseBody
    public Map updatePrevLocation(String ids, String id, String pid) {

        Map map = new HashMap(16);

        //先更改待排节点要挂载到目标节点的pId
        if (menuService.updateMenuLocation(id, pid)) {
            //调整目标节点同级的所有位置
            menuService.updateMenuLocation(ids);
            map.put(ConstantStr.MESSAGE, "调整成功");
        } else {
            map.put(ConstantStr.MESSAGE, "调整失败");
        }
        return map;
    }

    @RequestMapping(value = "/addMenuNode", method = RequestMethod.POST) @ResponseBody
    public Map addMenuNode(Menu menu) {

        Map map = new HashMap(16);

        if (menu.getType() == 1) {
            menu.setHasChild(1);
        } else {
            menu.setHasChild(0);
        }
        if (StringUtils.isBlank(menu.getUrl())){
            menu.setUrl("javascript:;");
        }
        menuService.addMenuNode(menu);

        map.put(ConstantStr.MESSAGE, "添加成功");
        return map;
    }

    @RequestMapping(value = "/deleteMenuNode", method = RequestMethod.GET) @ResponseBody
    public Map deleteMenuNode(String id) {

        Map map = new HashMap(16);

        menuService.deleteMenuNode(id);
        map.put(ConstantStr.MESSAGE, "删除成功");
        return map;
    }

    @RequestMapping(value = "/renameMenuNode", method = RequestMethod.GET) @ResponseBody
    public Map renameMenuNode(String id, String newName) {
        Map map = new HashMap(16);

        menuService.renameMenuNode(id, newName);
        map.put(ConstantStr.MESSAGE, "更新成功");
        return map;
    }

}
