package com.bankapp.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClerkRequest {
	@NotBlank(message = "{clerk.name.notBlank}")
    private String name;

    @NotNull(message = "{clerk.managerId.notNull}")
    private Integer managerId;
    
    @NotBlank(message = "{username.notBlank}")
    private String username;
    
    @NotBlank(message = "{password.notBlank}")
    private String password;
}
