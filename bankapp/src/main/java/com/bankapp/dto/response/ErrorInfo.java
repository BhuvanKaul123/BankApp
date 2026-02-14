package com.bankapp.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo {
	private String errorMessage;
	private String statusCode;
	private String toContact;
	private LocalDateTime timestamp;
}
