package com.snbc.rabbitmq;

import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.snbc.rabbitmq.config.HelloSender;
import com.snbc.rabbitmq.config.SenderConf;

/**
 * @className Test.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年8月14日 下午1:33:59
 * @version V1.0
 * @copyright SNBC 2011
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private SenderConf senderConf;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @org.junit.Test
    public void hello() throws Exception {
        //helloSender.send();
    	this.rabbitTemplate.convertAndSend("exchange","topic.message","hello,rabbit~~~11");
    	    this.rabbitTemplate.convertAndSend("exchange","topic.messages","hello,rabbit~~~22");
    }
   
}
