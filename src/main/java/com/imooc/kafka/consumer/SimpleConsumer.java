package com.imooc.kafka.consumer;


import com.google.gson.Gson;
import com.imooc.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author yangj
 */
@Slf4j
@Component
public class SimpleConsumer {
    private final Gson gson = new Gson();

    @KafkaListener(topics = "${spring.kafka.topic.default}", containerFactory = "ackContainerFactory")
    public void receive(String message, Acknowledgment acknowledgment) {
        try {
            String json = gson.toJson(message);
            log.info(json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}