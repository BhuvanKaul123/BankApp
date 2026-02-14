package com.bankapp.dto.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequest {
	@NotBlank(message = "{transfer.toAccountId.notBlank}")
    private String toAccountIdString;

    @NotNull(message = "{transfer.amount.notNull}")
    @Positive(message = "{transfer.amount.positive}")
    private BigDecimal amount;
}
