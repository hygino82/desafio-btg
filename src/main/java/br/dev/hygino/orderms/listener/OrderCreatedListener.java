package br.dev.hygino.orderms.listener;

import static br.dev.hygino.orderms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import br.dev.hygino.orderms.dto.OrderCreatedEvent;

@Component
public class OrderCreatedListener {

	private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

	@RabbitListener(queues = ORDER_CREATED_QUEUE)
	public void listen(Message<OrderCreatedEvent> message) {
		logger.info("Message consumed: {}" , message);
	}
}
