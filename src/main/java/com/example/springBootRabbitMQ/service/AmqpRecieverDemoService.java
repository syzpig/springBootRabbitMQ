package com.example.springBootRabbitMQ.service;

import com.example.springBootRabbitMQ.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 队列接收示例服务  使用jms （java message sevice） java消息服务
 * JMS是java的消息服务，JMS的客户端之间可以通过JMS服务进行异步的消息传输。 它其实就是个API  简单的说就是一套访问消息系统的标准接口，提供JMS服务的产品都可以用这套接口去访问其服务，就好像可以用java.sql包的接口去访问各种提供JDBC驱动的数据库一样
 * 它提供标准的产生、发送、接收消息的接口简化企业应用的开发
 * 应用场景： 用于消息中间件。
 * 第一想到的就是异步处理.大多数的回答是:用JMS来异步 发送邮件!
 * 其实,还可以用JMS来解决很多复杂的问题,例如 分布,并发,系统解耦,负载均衡,热部署,触发器等等,
 * 这些复杂问题因为引入JMS而变的更加简单
 * http://itindex.net/detail/49721-jms-jms-%E5%BA%94%E7%94%A8
 * 应用具体讲解：https://blog.csdn.net/he90227/article/details/50800646
 */
@Service
public class AmqpRecieverDemoService {

	private static final Logger logger = LoggerFactory.getLogger(AmqpRecieverDemoService.class);

	/**
	 *创建Connection对象的工厂，针对两种不同的jms消息模型，分别有QueueConnectionFactory和TopicConnectionFactory两种。可以通过JNDI来查找ConnectionFactory对象。
	 */
	@Autowired
	private ConnectionFactory connectionFactory;

	/**
	 *SimpleMessageListenerContainer:消息监听容器配置
	 * https://blog.csdn.net/sn_gis/article/details/41252109
	 */
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(AmqpConfig.DEFAULT_QUEUE);
		container.setExposeListenerChannel(true);//是否暴露监听通道
		container.setMaxConcurrentConsumers(1);//并发消费者的最大数目。必须大于或者等于并发消费者的数目。
		container.setConcurrentConsumers(1);//在初始化监听器的时候，并发消费者的数目。
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
			byte[] body = message.getBody();
			logger.info("收到消息: " + new String(body));
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); // 确认消息成功消费
		});
		return container;
	}
}
