package com.snbc.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;

/**
 * @className HelloReceiver.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年8月14日 下午1:33:32
 * @version V1.0
 * @copyright SNBC 2011
 */
@Component
public class HelloReceiver {
	/**
	 * 简单队列
	 * 
	 * @funciton
	 * @author liuxiang2
	 * @param hello
	 * @time 2019年12月13日 下午3:34:32
	 */
	
	  @RabbitListener(queues = "q_hello")
	  @RabbitHandler public void process(String hello) {
		  i++;
	  System.out.println("第"+i+"Receiver  : " + hello); }
	  @RabbitListener(queues = "q_hello")
	  @RabbitHandler public void process1(String hello) {
		  j++;
	  System.out.println("第"+j+"Receiver1  : " + hello); }
	  int i= 0;	
	  int j =0;
	 /**
		 * 广播模式
		 */
	
	  @RabbitListener(bindings = @QueueBinding(value =
	  
	  @Queue(value = "ly-work-queue", durable = "true"), exchange
	  = @Exchange(value= "message", ignoreDeclarationExceptions = "true", type
	  =ExchangeTypes.FANOUT))) public void fanout(String work) { j++;
	  System.out.println("第"+j+"WorkReceiver  : " + work); }
	  
	  @RabbitListener(bindings = @QueueBinding(value =
	  
	  @Queue(value = "ly-work-queue1", durable = "true"), exchange
	  = @Exchange(value = "message", ignoreDeclarationExceptions = "true", type =
	  ExchangeTypes.FANOUT))) public void fanout1(String work) { i++;
	  System.out.println("第"+i+"WorkReceiver1  : " + work);
	  
	  }
	 
			  /**
				 * head模式
				 */
					  @RabbitListener(bindings = @QueueBinding(value =
					  @Queue(value = "ly-heads-queue", durable = "true"), 
					  exchange = @Exchange(value= "message1", ignoreDeclarationExceptions = "true", 
					  type =ExchangeTypes.DIRECT),
			            key = "direct")) 
					  public void work(String work) 
					  {
						  j++;
						  System.out.println("第"+j+"HEADRDSeceiver  : " + work); 
						  }
					  @RabbitListener(bindings = @QueueBinding(value =
							  @Queue(value = "ly-heads-queue1", durable = "true"), 
							  exchange = @Exchange(value = "message1", ignoreDeclarationExceptions = "true", 
							  type = ExchangeTypes.DIRECT),
					            key = "direct")) 
					  public void work1(String work) 
					  {
						  i++;
						  System.out.println("第"+i+"HEADRDSeceiver1  : " + work); 
						  	
					  }

}
