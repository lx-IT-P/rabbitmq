package com.snbc.rabbitmq.config;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @className 工作模式WorkQueue.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年12月13日 下午3:19:15
 * @version V1.0
 * @copyright SNBC 2011
 */
@Component
public class WorkQueue {
	 @Autowired
	    private AmqpTemplate rabbitTemplate;
	 public void send() {
	        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(0));//24小时制
	        String context = "工作模式 " + date;
	        System.out.println("Sender : " + context);
	        for (int i = 0; i < 100; i++) {
	        	//简单对列的情况下routingKey即为Q名
		        this.rabbitTemplate.convertAndSend("message1","direct", context);
			}
	        
	    }
}
