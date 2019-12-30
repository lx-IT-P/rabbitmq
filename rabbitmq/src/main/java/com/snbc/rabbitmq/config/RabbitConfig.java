package com.snbc.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @className RabbitConfig.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年8月14日 下午1:31:36
 * @version V1.0
 * @copyright SNBC 2011
 */
@Configuration
@ContextConfiguration
public class RabbitConfig {

		@Bean
	    public Queue queue() {
	        return new Queue("q_hello");
	    }
}
