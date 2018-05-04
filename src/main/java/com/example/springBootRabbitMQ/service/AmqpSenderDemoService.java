package com.example.springBootRabbitMQ.service;

import com.example.springBootRabbitMQ.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 队列发送示例服务
 */
@Service
public class AmqpSenderDemoService {

	private static final Logger logger = LoggerFactory.getLogger(AmqpSenderDemoService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	// 发送消息
	public void sendMsg(String msg) {
		rabbitTemplate.convertAndSend(AmqpConfig.DEFAULT_EXCHANGE, AmqpConfig.DEFAULT_ROUTINGKEY, msg);
		logger.info("消息发送成功");
	}

}
