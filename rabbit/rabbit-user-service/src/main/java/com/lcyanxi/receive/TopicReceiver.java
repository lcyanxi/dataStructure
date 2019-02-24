package com.lcyanxi.receive;

import com.lcyanxi.topic.TopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by lcyanxi on 2019/2/24
 */
@Component
public class TopicReceiver {
    // queues是指要监听的队列的名字
    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE1)
    public void  receiveTopic1(String user) {
        System.out.println("【receiveTopic1监听到消息】" + user.toString());

    }

}
