package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 简单队列一对一
 */
public class Send {

  private  static final String QUEUE_NAME="test_simple_queue";

  public static void main(String[] args) throws Exception{

    Connection connection = ConnectionUtils.getConnection();

    //获取一个管道
    Channel channel = connection.createChannel();

    ///创建队列声明
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    String msg="hello simple ! ";

    channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

    System.out.println("---send msg---");

    channel.close();

    connection.close();

  }

}
