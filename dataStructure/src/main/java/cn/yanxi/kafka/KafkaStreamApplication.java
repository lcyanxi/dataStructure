package cn.yanxi.kafka;


import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * Created by lcyanxi on 2019/1/19
 * kafka streams
 * 实时处理单词带有”>>>”前缀的内容。例如输入”atguigu>>>ximenqing”，最终处理成“ximenqing”
 */

public class KafkaStreamApplication {
    public static void main(String[] args) {
        // 定义输入的topic
        String from = "first";
        // 定义输出的topic
        String to = "second";
        //设置参数
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"logFilter");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"lcyanxi02:9092");
        StreamsConfig config=new StreamsConfig(properties);

        //构建拓扑
        TopologyBuilder builder=new TopologyBuilder();
        builder.addSource("source",from)
                .addProcessor("process", new ProcessorSupplier() {
                    public Processor get() {
                        return new LogProcessor();
                    }
                },"source")
                .addSink("sink",to,"process");


        //创建kafka stream
        KafkaStreams streams=new KafkaStreams(builder,config);
        streams.start();

    }

}
