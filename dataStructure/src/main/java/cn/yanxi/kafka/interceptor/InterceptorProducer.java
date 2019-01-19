package cn.yanxi.kafka.interceptor;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lcyanxi on 2019/1/19
 *
 * 需求：
 实现一个简单的双interceptor组成的拦截链。
 第一个interceptor会在消息发送前将时间戳信息加到消息value的最前部；
 第二个interceptor会在消息发送后更新成功发送消息数或失败发送消息数。
 */

public class InterceptorProducer {
    public static void main(String[] args) throws Exception {
        // 1 设置配置信息
        Properties props = new Properties();
        props.put("bootstrap.servers", "lcyanxi02:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //构建拦截链
        List<String> interceptors=new ArrayList<String>();
        interceptors.add("cn.yanxi.kafka.interceptor.TimeInterceptor");
        interceptors.add("cn.yanxi.kafka.interceptor.CounterInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);

        String topic="first";
        Producer<String,String> producer=new KafkaProducer<String, String>(props);

        //发送消息
        for (int i=0;i<10;i++){
            ProducerRecord<String,String> record=new ProducerRecord<String, String>(topic,"message"+i);
            producer.send(record);
        }

// 4 一定要关闭producer，这样才会调用interceptor的close方法
        producer.close();

    }
}
