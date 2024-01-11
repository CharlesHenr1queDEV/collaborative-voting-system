package br.com.collaborativevotingsystem.configuration.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.collaborativevotingsystem.enums.RabbitQueueEnum;

@Configuration
public class RabbitMQConfig {

	@Bean
	public Queue votingResultQueue() {
		return QueueBuilder.nonDurable(RabbitQueueEnum.RESULT.getRabbitQueueName()).build();
	}

	@Bean
	public DirectExchange myExchange() {
		return new DirectExchange("collaborative.voting.exchange");
	}

	@Bean
	public MessageConverter jackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Binding bindingRunAccountant(DirectExchange directExchange, Queue runGenericAccountantQueue) {
		return BindingBuilder.bind(runGenericAccountantQueue).to(directExchange)
				.with(RabbitQueueEnum.RESULT.getRabbitKey());
	}

}
