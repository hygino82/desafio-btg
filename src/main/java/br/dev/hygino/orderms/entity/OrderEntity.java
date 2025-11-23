package br.dev.hygino.orderms.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tb_orders")
@Data
@NoArgsConstructor
public class OrderEntity {

	@MongoId
	private Long orderId;

	@Indexed(name = "customer_id_index")
	private Long customerId;

	@Field(targetType = FieldType.DECIMAL128)
	private BigDecimal total;

	private List<OrderItem> items;
}
