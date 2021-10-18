package com.javainuse.controller;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.javainuse.config.RabbitMQConfig;

@RestController
public class Sender {

	@Autowired RabbitTemplate rabbitTemplate;
	@Autowired RabbitAdmin rabbitAdmin;
	@Autowired TopicExchange exchange;

	@GetMapping(value = "/producer/{user}/{msg}")
	@ResponseStatus(code = HttpStatus.OK)
	public String producerString(@PathVariable("user") String routingKey,@PathVariable("msg") String message) {
		
		Queue queue = new Queue(routingKey, true, false, false, RabbitMQConfig.getArguments());
		rabbitAdmin.declareQueue(queue);
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);//create b
		rabbitAdmin.declareBinding(binding);//send t rmqs
		rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);

		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}
}

