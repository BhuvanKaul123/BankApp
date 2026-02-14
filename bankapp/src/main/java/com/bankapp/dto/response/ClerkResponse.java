package com.bankapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClerkResponse {
	private Integer empId;
	private String name;
	private ManagerResponse manager;
}