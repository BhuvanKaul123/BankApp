package com.bankapp.dto.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
	
	@NotBlank(message = "{account.holderName.notBlank}")
	private String holderName;
	
	@NotNull(message = "{account.balance.notNull}")
    @PositiveOrZero(message = "{account.balance.positiveOrZero}")
	private BigDecimal balance;
}