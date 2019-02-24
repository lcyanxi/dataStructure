package com.lcyanxi.web;

import com.lcyanxi.model.User;
import com.lcyanxi.receive.TopicReceiver;
import com.lcyanxi.sender.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcyanxi on 2019/2/24
 */

@RestController
@RequestMapping(value = "/user")
public class UserControll {

    @Autowired
    private TopicSender topicSender;
    @Autowired
    private TopicReceiver topicReceiver;

    @GetMapping(value = "/orderInfo")
    public String UserOrderInfo(String username){
        User user=new User();
        user.setAge(22);
        user.setId(1111);
        user.setName(username);
        topicSender.send(username);
        topicReceiver.receiveTopic1(username);

        return "成功";
    }
}
