package com.bankapp.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

        @NotBlank(message = "{username.notBlank}")
        String username,

        @NotBlank(message = "{password.notBlank}")
        String password

) {}
