package com.example.springBootRabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置类
 * 此处主要实现与RabbitMQ的初始化连接、绑定、建立、向Spring容器注入相关工具等功能
 * 原则上此个性化配置可以封装在自定义amqp-starter中
 */
@Configuration
public class AmqpConfig {

	public static final String DEFAULT_EXCHANGE = "demo-service-default-exchange";
	public static final String DEFAULT_ROUTINGKEY = "demo-service-default-routingkey";
	public static final String DEFAULT_QUEUE = "demo-service-default-queue";

	// FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	// HeadersExchange ：通过添加属性key-value匹配
	// DirectExchange:按照routingkey分发到指定队列
	// TopicExchange:多关键字匹配
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(DEFAULT_EXCHANGE);
	}

	@Bean
	public Queue queue() {
		return new Queue(DEFAULT_QUEUE, true); // 队列持久
	}

	// 将Exchange，RoutingKey与Queue建立绑定关系
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(DEFAULT_ROUTINGKEY);
	}
}
