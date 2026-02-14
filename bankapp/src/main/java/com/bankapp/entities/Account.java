package com.bankapp.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String holderName;
	private BigDecimal balance;
	
	@OneToMany(mappedBy = "initiatorAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankTransaction> transactions;
}
