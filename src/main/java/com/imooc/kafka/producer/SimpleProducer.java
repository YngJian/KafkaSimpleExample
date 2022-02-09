package com.imooc.kafka.producer;

import com.google.gson.Gson;
import com.imooc.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SimpleProducer {
    private final Gson gson = new Gson();

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, MessageEntity message) {
        kafkaTemplate.send(topic, gson.toJson(message));
    }

    public void send(String topic, String key, MessageEntity entity) {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                topic,
                key,
                gson.toJson(entity));

        long startTime = System.currentTimeMillis();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime, key, gson.toJson(entity)));
    }

}