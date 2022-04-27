package com.example.Base58_practice.rabbitMQConsumer;

import com.example.Base58_practice.config.MessagingConfig;
import com.example.Base58_practice.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    private void receiveA(UserDto userDto) {
        log.info("Message received from queue -> {}", userDto);
    }

}