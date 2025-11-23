package br.dev.hygino.orderms.dto;

import java.math.BigDecimal;

public record OrderItemEvent(
		String produto, 
		Integer quantidade, 
		BigDecimal preco) {
}
