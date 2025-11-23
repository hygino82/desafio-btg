package br.dev.hygino.orderms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.hygino.orderms.dto.ApiResponse;
import br.dev.hygino.orderms.dto.OrderResponse;
import br.dev.hygino.orderms.dto.PaginationResponse;
import br.dev.hygino.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @GetMapping("customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable Long customerId,
                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<OrderResponse> pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));

        logger.info("Página do cliente {}", customerId);

        var content = pageResponse.getContent();

        logger.info("Página do cliente {}", customerId);

        if (!content.isEmpty()) {
            logger.info(content.getFirst().orderId().toString());
        } else {
            logger.error("Nenhum pedido encontrado para o cliente {}", customerId);
        }


        return ResponseEntity.ok(new ApiResponse<OrderResponse>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)));
    }
}
