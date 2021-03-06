package com.lcyanxi.sender;

import com.lcyanxi.model.User;
import com.lcyanxi.topic.TopicConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lcyanxi on 2019/2/24
 */
@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    // 第一个参数：TopicExchange名字
    // 第二个参数：Route-Key
    // 第三个参数：要发送的内容
    public void send(String  user) {
        this.rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE, "topic.order", user);

    }
}
