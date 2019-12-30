package com.snbc.rabbitmq.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snbc.rabbitmq.App;

/**
 * @className QueueTest.java
 * @funciton
 * @author liuxiang2
 * @CreatedTime: 2019年12月13日 下午3:49:40
 * @version V1.0
 * @copyright SNBC 2011
 */
@SpringBootTest(classes=App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueueTest {
	    @Autowired
	    private HelloSender helloSender;
	    @Autowired
	    private WorkQueue workQueue;
	    @Test
	    public void testRabbit() {
	        helloSender.send();
	    }
	/*
	 * @Test public void testWorkRabbit() { workQueue.send(); }
	 */
}
