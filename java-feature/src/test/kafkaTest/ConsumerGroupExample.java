package com.test.kafkaTest;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumerGroupExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("auto.offset.reset", "smallest");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "pv");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        ConsumerConfig conf = new ConsumerConfig(props);

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(conf);

        String topic = "page_visits";
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        for(final KafkaStream<byte[], byte[]> stream: streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                System.out.println("message: " + new String(it.next().message()));
            }
        }

        if (consumer != null)
            consumer.shutdown();
    }
}
