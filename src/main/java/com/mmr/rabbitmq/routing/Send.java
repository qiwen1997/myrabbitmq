package com.mmr.rabbitmq.routing;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式
 * 生产者按routing key发送消息，不同的消费者端按不同的routing key接收消息
 */
public class Send {

  private static final String EXCHANGE_NAME="test_exchange_direct";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //获取一个管道
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME,"direct");

    String msg="hello direct!";

    String rountingKey="info";
    channel.basicPublish(EXCHANGE_NAME,rountingKey,null,msg.getBytes());

    System.out.println("send"+msg);
    channel.close();
    connection.close();
  }
}
