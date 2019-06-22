package com.mmr.rabbitmq.SpringBoot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqHelloApplicationTests {

  @Autowired
  private Sender sender;

  @Autowired
  private Receiver receiver;

  @Test
  public void hello() throws Exception {
    sender.send();
    //receiver.process();
  }

}
