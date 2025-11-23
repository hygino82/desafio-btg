package br.dev.hygino.orderms.dto;

import java.util.List;
public record ApiResponse<T>(
		List<T> data,
		PaginationResponse pagination
		) {
}
