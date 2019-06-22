package com.mmr.rabbitmq.work;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 工作队列 一个生产者端，多个消费者端。
 * 采用轮询分发的方式，收一个发一个
 */
public class Send {

  private  static final String QUEUE_NAME="test_work_queue";

  public static void main(String[] args) throws Exception {

    Connection connection = ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    for(int i=0;i<50;i++){

      String msg="hello"+i;

      channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
      Thread.sleep(i+20);
    }

    channel.close();
    connection.close();
  }
}
