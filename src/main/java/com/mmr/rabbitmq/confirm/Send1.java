package com.mmr.rabbitmq.confirm;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 单条confirm
 */
public class Send1 {

  private static final String QUEUE_NAME="test_queue_confirm1";

  public static void main(String[] args) throws Exception {

    Connection connection= ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    //生产者调用confirmSelect将channel设置成confirm模式
    channel.confirmSelect();

    String msgString="hello confirm message";

    channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());

    if(!channel.waitForConfirms()){
      System.out.println("message send failed");
    }else{
      System.out.println("message send ok");
    }
    channel.close();
    connection.close();
  }
}
