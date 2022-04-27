package com.example.Base58_practice.service;

import com.example.Base58_practice.config.MessagingConfig;
import com.example.Base58_practice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqSender {



    private final AmqpTemplate rabbitTemplate;


    public void send(UserDto userDto) {
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTINGKEY, userDto);
        System.out.println("Send msg = " + userDto);

    }

}