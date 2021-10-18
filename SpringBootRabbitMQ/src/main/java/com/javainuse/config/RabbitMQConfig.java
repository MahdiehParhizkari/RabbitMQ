package com.javainuse.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	@Value("${spring.rabbitmq.host}") private String host;
	@Value("${spring.rabbitmq.port}") private Integer port;
	@Value("${spring.rabbitmq.username}") private String username;
	@Value("${spring.rabbitmq.password}") private String password;
	@Value("${rabbitmq.exchange}") private String exchange;

	/*			Sender/Receiver Configuration			*/	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(exchange,Boolean.TRUE,Boolean.FALSE);
	}
	@Bean 
	public RabbitAdmin getRabbitAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory); 
	}
	public static Map<String, Object> getArguments() {
		Map<String, Object> arguments=new HashMap<String, Object>();
		arguments.put("x-message-ttl",60480000);//100*60*60*24*7=1Week
		arguments.put("x-expires", 1800000);//idle Queue : 100*60*60*5=5Hours
		arguments.put("x-max-length", 100);//message
		arguments.put("x-max-length-bytes", 3145728);//1024*1024*3=3MByte
		arguments.put("x-queue-mode", "lazy");//Saved message on HDD
		return arguments; 
	}

}