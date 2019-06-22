package com.mmr.rabbitmq.tx;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class txRecv {

  private static final String QUEUE_NAME="test_queue_tx";

  public static void main(String[] args) throws Exception {
    Connection connection= ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
        System.out.println("recv[tx] msg"+new String(body,"utf-8"));
      }
    });
  }
}
