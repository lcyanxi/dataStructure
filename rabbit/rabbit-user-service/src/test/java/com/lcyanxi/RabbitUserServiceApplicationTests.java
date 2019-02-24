package com.lcyanxi;

import com.lcyanxi.sender.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitUserServiceApplicationTests {
	private static final int NUM=100;
	private static CountDownLatch cdl=new CountDownLatch(NUM);

	@Autowired
	private TopicSender topicSender;

	@Test
	public void contextLoads() {
	}




}

