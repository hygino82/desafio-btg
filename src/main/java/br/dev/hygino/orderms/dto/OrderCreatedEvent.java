package br.dev.hygino.orderms.dto;

import java.util.List; 
public record OrderCreatedEvent(
		Long codigoPedido,
		Long CodigoCliente,
		List<OrderItemEvent> itens
		) {

}
