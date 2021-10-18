package com.javainuse.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project Accounting for Building and share work
 * @Author Mahdieh Parhizkari
 * @Date Oct 14, 2021 10:04:33 PM
 * @version
 * Created by Eclipse 2020-09
 * Email:       mahdieh.Parhizkari@gmail.com
 * Description: 
*/

@RestController
public class Receiver {
	@Autowired RabbitTemplate rabbitTemplate;
	
	@GetMapping(value = "/receive/{queuename}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getMsg(@PathVariable("queuename") String qName) {
		String result= (String) rabbitTemplate.receiveAndConvert(qName);
		return "Queue: " +qName+ "message: " +result;
	}

}
