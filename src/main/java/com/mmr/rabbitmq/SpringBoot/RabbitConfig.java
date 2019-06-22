package com.mmr.rabbitmq.SpringBoot;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {
  public static final String MIAOSHA_QUEUE = "hello";
  @Bean
  public Queue helloQueue() {
    return new Queue(MIAOSHA_QUEUE,true);
  }
}
