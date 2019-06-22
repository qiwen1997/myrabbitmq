package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Recv2 {
  private  static final String QUEUE_NAME="test_work_queue";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    //创建频道
    final Channel channel=connection.createChannel();

    //队列声明
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    channel.basicQos(1);//保证一个只发一个
    Consumer consumer=new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
        String msg=new String(body,"utf-8");

        System.out.println("[2] Recv msg:"+msg);

        try{
          Thread.sleep(1000);
        }catch (Exception e){
          e.printStackTrace();
        }finally {
          System.out.println("[2] done");
          channel.basicAck(envelope.getDeliveryTag(),false);//手动应答
        }
      }
    };

    boolean autoAck=false;
    channel.basicConsume(QUEUE_NAME,autoAck,consumer);

  }
}
