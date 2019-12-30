package com.snbc.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className ReceiverConf.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年8月14日 下午2:18:35
 * @version V1.0
 * @copyright SNBC 2011
 */
@Component
public class ReceiverConf {
	@RabbitListener(queues="topic.message")    //监听器监听指定的Queue
    public void process1(String str) {    
        System.out.println("message:"+str);
    }
    @RabbitListener(queues="topic.messages")    //监听器监听指定的Queue
    public void process2(String str) {
        System.out.println("messages:"+str);
    }
    
}
