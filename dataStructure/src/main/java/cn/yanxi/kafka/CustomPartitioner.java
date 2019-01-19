package cn.yanxi.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Created by lcyanxi on 2019/1/18
 * 自定义分区（新API）
 *
 * 需求：将所有数据存储到topic的第0号分区上
 * 定义一个类实现Partitioner接口，重写里面的方法
 */

public class CustomPartitioner implements Partitioner {
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
