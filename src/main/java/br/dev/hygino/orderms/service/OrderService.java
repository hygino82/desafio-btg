package br.dev.hygino.orderms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.orderms.dto.OrderCreatedEvent;
import br.dev.hygino.orderms.dto.OrderResponse;
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
        entity.setCustomerId(event.codigoCliente());
        entity.setItens(getOrderItems(event));
        entity.setTotal(getTotal(event));

        entity = orderRepository.save(entity);
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream().map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco())).toList();
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        Page<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }
}
