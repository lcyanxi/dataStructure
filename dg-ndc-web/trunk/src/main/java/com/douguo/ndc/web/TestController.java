package com.douguo.ndc.web;

import com.douguo.ndc.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/9/3
 */
@RestController @RequestMapping(value = "/test") public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private static final String SRCCAIPUFILE = "/opt/DATA/goldmine/src/web_service/dc-web/data/src_caipu_data.log";
    private static final String TARGETCAIPUFILE = "/opt/DATA/goldmine/src/web_service/dc-web/data/target_caipu_data.log";
    private static final String SRCNOTEFILE = "/opt/DATA/goldmine/src/web_service/dc-web/data/src_note_data.log";
    private static final String TARGETNOTEFILE = "/opt/DATA/goldmine/src/web_service/dc-web/data/target_note_data.log";

    @RequestMapping(value = "/caipu", method = RequestMethod.GET) public static void getCaipuRedisData()
        throws Exception {
        // 读取刚才导出的ES数据
        BufferedReader br = new BufferedReader(new FileReader(SRCCAIPUFILE));
        BufferedWriter out = new BufferedWriter(new FileWriter(TARGETCAIPUFILE, false));

        String id;

        while ((id = br.readLine()) != null) {
            String view = "Recipe_Views_" + id;
            String collect = "recipe_collector_count_" + id;
            String dish = "recipe_dishes_count_" + id;

            log.info(view);
            log.info(collect);

            String viewResult = RedisUtil.get(view);
            String collectResult = RedisUtil.get(collect);
            String viewDish = RedisUtil.get(dish);
            String commt = "0";

            log.info(viewResult);
            log.info(collectResult);

            out.write(viewResult + "," + collectResult + "," + viewDish + "," + commt);
            out.write("\r\n");

        }
        log.info("菜谱执行完毕--------------");
        out.close();
        br.close();

    }

    @RequestMapping(value = "/note", method = RequestMethod.GET) public static void getNoteRedisData()
        throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(SRCNOTEFILE));
        BufferedWriter out = new BufferedWriter(new FileWriter(TARGETNOTEFILE, false));

        String id;

        while ((id = br.readLine()) != null) {
            String noteLikes = "dg_user_liked_count_" + id + "_NOTE:LIKE";
            String noteCollect = "dg_user_collected_count_" + id + "_NOTE:COLLECT";

            String likes = RedisUtil.get(noteLikes);
            String collect = RedisUtil.get(noteCollect);
            String view = "0";
            String commt = "0";
            out.write(likes + "," + collect + "," + view + "," + commt);
            out.write("\r\n");

        }
        log.info("笔记执行完毕--------------");
        out.close();
        br.close();

    }

}
