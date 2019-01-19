package cn.yanxi.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * Created by lcyanxi on 2019/1/19
 */

public class LogProcessor implements Processor<byte[],byte[]>{
    private ProcessorContext context;

    public void init(ProcessorContext processorContext) {
        this.context=processorContext;

    }

    public void process(byte[] bytes, byte[] bytes2) {
        String input=new String(bytes2);
        if (input.contains(">>>")){
            input=input.split(">>>")[1].trim();
            //输出到下一个topic
            context.forward("logProcessor".getBytes(),input.getBytes());
        }else {
            context.forward("logProcessor".getBytes(),input.getBytes());
        }

    }

    public void punctuate(long l) {

    }

    public void close() {

    }
}
