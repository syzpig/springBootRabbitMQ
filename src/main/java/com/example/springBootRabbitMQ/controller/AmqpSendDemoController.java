package com.example.springBootRabbitMQ.controller;

import com.example.springBootRabbitMQ.service.AmqpSenderDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送接口示例
 * 方便REST测试消息发送功能
 */
@RestController
@RequestMapping(value = "/send")
public class AmqpSendDemoController {

	@Autowired
	private AmqpSenderDemoService amqpSenderDemoService;


	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public String sendMsg() {
		amqpSenderDemoService.sendMsg("hello demo");
		return "success";
	}


}
