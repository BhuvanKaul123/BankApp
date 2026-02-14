package com.bankapp.enums;

public enum TransactionFailureReason {
	INSUFFICIENT_BALANCE,
    ACCOUNT_LOCKED,
    INVALID_AMOUNT,
    DAILY_LIMIT_EXCEEDED,
    SYSTEM_ERROR,
    MANAGER_REJECTED
}
