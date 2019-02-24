package com.lcyanxi.receive;

import com.lcyanxi.sender.TopicSender;
import com.lcyanxi.topic.TopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lcyanxi on 2019/2/24
 */
@Component
public class TopicReceiver {
    @Autowired
    private TopicSender topicSender;
    // queues是指要监听的队列的名字
    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String content) {
        System.out.println("【receiveTopic1监听到消息】" + content.toString());

        topicSender.send("topic.orderReceive",content+"-------------------------");
    }



}
