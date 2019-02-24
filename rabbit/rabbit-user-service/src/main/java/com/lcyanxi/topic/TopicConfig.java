package com.lcyanxi.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lcyanxi on 2019/2/24
 */

@Configuration
public class TopicConfig {
    //topic
    public static final String TOPIC_QUEUE1 = "topic.orderReceive";
    public static final String TOPIC_EXCHANGE = "exchange";



    @Bean(name="message")
    public Queue queueMessages() {
        return new Queue("topic.order");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.order");
    }


}
