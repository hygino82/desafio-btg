package br.dev.hygino.orderms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.hygino.orderms.dto.OrderCreatedEvent;
import br.dev.hygino.orderms.entity.OrderEntity;
import br.dev.hygino.orderms.entity.OrderItem;
import br.dev.hygino.orderms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public void save(OrderCreatedEvent event) {
		OrderEntity entity = new OrderEntity();
		
		entity.setOrderId(event.codigoPedido());
		entity.setCustomerId(event.CodigoCliente());
		entity.setItems(getOrderItens(event));
		entity.setTotal(getTotal(event));
		
		orderRepository.save(entity);
	}

	private BigDecimal getTotal(OrderCreatedEvent event) {
	    return event.itens().stream()
	            .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
	            .reduce(BigDecimal::add)
	            .orElse(BigDecimal.ZERO);
	}


	private static List<OrderItem> getOrderItens(OrderCreatedEvent event) {
		return event.itens().stream()
				.map(i -> 
				new OrderItem(
						i.produto(), 
						i.quantidade(), 
						i.preco()))
				.toList();
	}
}
