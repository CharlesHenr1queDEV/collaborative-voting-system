package br.com.collaborativevotingsystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.collaborativevotingsystem.dto.VotingResultDTO;
import br.com.collaborativevotingsystem.enums.RabbitQueueEnum;

@Service
public class RabbitMessageSenderService {
	
	private final Logger logger = LogManager.getLogger(ScheduleService.class);

	private RabbitTemplate rabbitTemplate;

	private DirectExchange directExchange;

	public RabbitMessageSenderService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
		this.rabbitTemplate = rabbitTemplate;
		this.directExchange = directExchange;
	}

	public void sendMessage(RabbitQueueEnum result, Object message) throws Exception {
		try {
			logger.info("[MESSAGE_SERVICE] Enviando mensagem para a fila " + result.getRabbitQueueName());
			rabbitTemplate.convertAndSend(directExchange.getName(), result.getRabbitKey(), message);
		} catch (Exception e) {
			logger.info("[MESSAGE_SERVICE] " + e.getMessage());
			throw e;
		}
	}

}
