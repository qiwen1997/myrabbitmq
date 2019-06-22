package com.mmr.rabbitmq.topic;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Recv2 {

  private static final String EXCHANGE_NAME="test_exchange_topic";
  private static final String QUEUE_NAME="test_queue_topic_2";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //获取一个管道
    final Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);


   //#主题全匹配 ， *匹配一个字符
    channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"goods.#");

    channel.basicQos(1);//保证一个只发一个

    Consumer consumer=new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
        String msg=new String(body,"utf-8");

        System.out.println("[2] Recv msg:"+msg);

        try{
          Thread.sleep(2000);
        }catch (Exception e){
          e.printStackTrace();
        }finally {
          System.out.println("[2] done");

          channel.basicAck(envelope.getDeliveryTag(),false);//手动应答
        }
      }
    };

    boolean autoAck=false;//自动应答改为false
    channel.basicConsume(QUEUE_NAME,autoAck,consumer);

  }

}