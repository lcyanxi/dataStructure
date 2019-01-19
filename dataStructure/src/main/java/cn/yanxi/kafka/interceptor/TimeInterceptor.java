package cn.yanxi.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by lcyanxi on 2019/1/19
 * kafka拦截器
 * 在消息发送前将时间戳信息加到消息value的最前部
 */

public class TimeInterceptor implements ProducerInterceptor<String,String> {
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {

        //业务部分

        return new ProducerRecord<String, String>(producerRecord.topic(),producerRecord.partition(),
                producerRecord.timestamp(),producerRecord.key(),System.currentTimeMillis()+","+producerRecord.value());
    }

    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
