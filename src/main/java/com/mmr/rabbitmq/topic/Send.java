package com.mmr.rabbitmq.topic;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式
 * 生产者端不只按固定的routing key发送消息，而是按字符串“匹配”发送，消费者端同样如此。
 * 符号“#”匹配一个或多个词，符号“*”仅匹配一个词。
 */
public class Send {

  private static final String EXCHANGE_NAME="test_exchange_topic";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //获取一个管道
    Channel channel = connection.createChannel();

    //声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

    String msgString="商品......";
    //主题模式匹配符goods.delete
    channel.basicPublish(EXCHANGE_NAME,"goods.delete",null, msgString.getBytes());

    System.out.println("-----send"+msgString);

    channel.close();
    connection.close();

  }
}
