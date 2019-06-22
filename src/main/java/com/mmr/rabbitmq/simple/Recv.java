package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * 消费者获取消息
 */
public class Recv {

  private  static final String QUEUE_NAME="test_simple_queue";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //创建频道
    Channel channel=connection.createChannel();

    //队列声明
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    //定义消费者
    DefaultConsumer consumer=new DefaultConsumer(channel){
     //获取到达的消息
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
        String msg = new String(body,"utf-8");
        System.out.println("new api recv"+msg);
      }
    };

    //监听队列
    channel.basicConsume(QUEUE_NAME,true,consumer);
  }
}
