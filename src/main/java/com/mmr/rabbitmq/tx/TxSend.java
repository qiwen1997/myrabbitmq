package com.mmr.rabbitmq.tx;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消息确认机制之事务机制
 *
 */
public class TxSend {

  private static final String QUEUE_NAME="test_queue_tx";

  public static void main(String[] args) throws Exception {
    Connection connection=ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    String msgString="hello tx message";

    try {
      channel.txSelect();

      channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());

      //int s=1/0;验证异常回滚

      System.out.println("send "+msgString);
      channel.txCommit();
    }catch(Exception e){
      channel.txRollback();
      System.out.println("send message txRollback");
    }
    channel.close();
    connection.close();
  }
}
