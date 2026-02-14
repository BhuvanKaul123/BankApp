package com.bankapp.dto.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DepositRequest {
	@NotNull(message = "{amount.notNull}")
    @Positive(message = "{amount.positive}")
	private BigDecimal amount;
}
