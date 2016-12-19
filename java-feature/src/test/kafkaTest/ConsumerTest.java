package com.test.kafkaTest;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by Moriatry on 15-6-25.
 */
public class ConsumerTest implements Runnable {
    private KafkaStream<byte[], byte[]> stream;
    private int threadNum;

    public ConsumerTest(KafkaStream<byte[], byte[]> stream, int threadNum) {
        this.stream = stream;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            System.out.println("#threadId: " + threadNum + ", message: " + new String(it.next().message()));
        }
    }
}
