package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消费者发送确认信息后继续分发
 */
public class Send {

  private  static final String QUEUE_NAME="test_work_queue";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    //
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    /**
     * 每个消费者发送确认信息之前，消息队列不发送下一个信息到消费者
     * 一次只处理一个信息
     * 限制发送给同一个消费者不的超过一条消息
     */
    int prefetchCount=1;
    channel.basicQos(prefetchCount);

    for(int i=0;i<50;i++){

      String msg="hello"+i;

      System.out.println("[WQ ]send"+msg);
      channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
      Thread.sleep(i+20);
    }

    channel.close();
    connection.close();
  }
}
