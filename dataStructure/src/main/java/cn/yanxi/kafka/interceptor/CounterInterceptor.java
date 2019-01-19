package cn.yanxi.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by lcyanxi on 2019/1/19
 * 统计发送消息成功和发送失败消息数，并在producer关闭时打印这两个计数器
 */

public class CounterInterceptor implements ProducerInterceptor<String,String> {

    private int SUCCESSNUM=0;
    private int ERRORNUM=0;

    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e==null){
            SUCCESSNUM++;
        }else {
            ERRORNUM++;
        }

    }

    public void close() {
        System.out.println("success:"+SUCCESSNUM+"--error:"+ERRORNUM);

    }

    public void configure(Map<String, ?> map) {

    }
}
