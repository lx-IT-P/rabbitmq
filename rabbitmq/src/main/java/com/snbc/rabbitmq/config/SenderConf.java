package com.snbc.rabbitmq.config;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className SenderConf.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年8月14日 下午2:14:38
 * @version V1.0
 * @copyright SNBC 2011
 */
@Configuration
public class SenderConf {
	 
        @Bean(name="message")
        public Queue queueMessage() {
            return new Queue("topic.message");
        }

        @Bean(name="messages")
        public Queue queueMessages() {
            return new Queue("topic.messages");
        }

        @Bean
        public TopicExchange exchange() {
            return new TopicExchange("exchange");
        }

        @Bean
        Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange) {
            return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
           
          
        }

        @Bean
        Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages, TopicExchange exchange) {
            return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");//*表示一个词,#表示零个或多个词
            
        }
        
       
}
