package com.mmr.rabbitmq.ps;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布订阅模式
 * 一个生产者，一个交换机，多个队列，每个队列对应一个消费者
 *
 */
public class Send {

  private static final String EXCHANGE_NAME="test_exchange_fanout";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //获取一个管道
    Channel channel = connection.createChannel();

    //声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//分发

    String msg="hello ps";

    channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

    System.out.println("Send:"+msg);

    channel.close();
    connection.close();
  }
}
