package br.com.collaborativevotingsystem.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageSenderService {

	private RabbitTemplate rabbitTemplate;

	private DirectExchange directExchange;

	public RabbitMessageSenderService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
		this.rabbitTemplate = rabbitTemplate;
		this.directExchange = directExchange;
	}

	public void sendMessage(String routingKey, Object message) throws Exception {
		try {
			rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, message);
		} catch (Exception e) {
			throw e;
		}
	}

}
