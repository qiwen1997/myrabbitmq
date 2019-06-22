package com.mmr.rabbitmq.util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

  public static Connection getConnection() throws Exception{
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("127.0.0.1");

    factory.setPort(5672);

    factory.setVirtualHost("/vhost_mmr");

    factory.setUsername("root");

    factory.setPassword("root");

    return factory.newConnection();
  }
}
