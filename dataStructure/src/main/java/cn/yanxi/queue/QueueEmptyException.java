package cn.yanxi.queue;

/**
 * Created by lcyanxi on 16-12-10.
 */
public class QueueEmptyException extends RuntimeException {
    public QueueEmptyException(String message) {
        super(message);
    }
}
