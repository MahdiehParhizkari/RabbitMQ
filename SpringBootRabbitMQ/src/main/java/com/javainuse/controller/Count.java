package com.javainuse.controller;
/**
 * @Project Accounting for Building and share work
 * @Author Mahdieh Parhizkari
 * @Date Oct 14, 2021 8:59:16 PM
 * @version
 * Created by Eclipse 2020-09
 * Email:       mahdieh.Parhizkari@gmail.com
 * Description: 
*/

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Count {
	
	@Autowired RabbitAdmin rabbitAdmin;
	
	@GetMapping(value = "/count/{qName}")
	public String send(@PathVariable("qname") String qName) {
		return rabbitAdmin.getQueueProperties(qName).get(RabbitAdmin.QUEUE_MESSAGE_COUNT).toString();
	}
}
