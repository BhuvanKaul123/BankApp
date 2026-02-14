package com.bankapp.utilities;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.bankapp.exceptions.InvalidRequestException;

@Component
public class StrictUuidConverter {

    public static UUID convertStringToUUID(String source) {
        if (!source.matches(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new InvalidRequestException("Invalid Account Id, expected UUID format");
        }
        return UUID.fromString(source);
    }
}
