package com.bankapp.utilities;

import org.springframework.http.HttpStatus;

import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.enums.TransactionStatus;

public class TransactionStatusHttpMapper {
	private TransactionStatusHttpMapper() {};
	
	public static HttpStatus map(BankTransactionDto dto) {
		
		// Successful or pending transaction, meaning transaction made, just waiting for approval
		if(dto.getTransactionStatus() == TransactionStatus.SUCCESSFUL) {
			return HttpStatus.CREATED;
		} 
		if(dto.getTransactionStatus() == TransactionStatus.PENDING) {
			return HttpStatus.ACCEPTED;
		}
		
		return switch (dto.getFailureReason()) {
        	case INSUFFICIENT_BALANCE -> HttpStatus.CONFLICT;
	        case ACCOUNT_LOCKED -> HttpStatus.LOCKED;
	        case INVALID_AMOUNT -> HttpStatus.BAD_REQUEST;
	        case DAILY_LIMIT_EXCEEDED -> HttpStatus.TOO_MANY_REQUESTS;
	        case SYSTEM_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
	        case MANAGER_REJECTED -> HttpStatus.ACCEPTED;
	    };
	}
}
