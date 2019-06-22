package com.mmr.rabbitmq.confirm;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 异步confirm
 */
public class Send3 {

  private static final String QUEUE_NAME="test_queue_confirm3";

  public static void main(String[] args) throws Exception {

    Connection connection= ConnectionUtils.getConnection();

    Channel channel=connection.createChannel();

    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    channel.confirmSelect();

    //未确认的消息
    final SortedSet<Long> confirmSet= Collections.synchronizedSortedSet(new TreeSet<Long>());

    //通道添加监听
    channel.addConfirmListener(new ConfirmListener() {
      //没有问题的handleAck
      public void handleAck(long deiveryTag, boolean multiple) throws IOException {

        if (multiple){
          System.out.println("---handleAck---multiple");
          confirmSet.headSet(deiveryTag+1).clear();
        }else{
          System.out.println("---handleAck---multiple false");
          confirmSet.remove(deiveryTag);
        }
      }

      //失败
      public void handleNack(long deiveryTag, boolean multiple) throws IOException {

        if (multiple){
          System.out.println("---handleNack---multiple");
          confirmSet.headSet(deiveryTag+1).clear();
        }else{
          System.out.println("---handleNack---multiple false");
          confirmSet.remove(deiveryTag);
        }
      }
    });

    String msgStr="sssssss";
    while(true){
      long seqNo = channel.getNextPublishSeqNo();
      channel.basicPublish("",QUEUE_NAME,null,msgStr.getBytes());
      confirmSet.add(seqNo);
    }
  }
}
