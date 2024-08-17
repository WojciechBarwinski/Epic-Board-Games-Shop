package com.wojciechbarwinski.demo.epic_board_games_shop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "ZIP code is required")
        @Pattern(regexp = "\\d{2}-\\d{3}", message = "Invalid ZIP code format. Expected format is XX-XXX")
        String zipCode,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\d{9}", message = "Invalid phone number format. Expected format is XXXXXXXXX") //this is just example and simple regex. For real purpose it should be much more specified.
        String phoneNumber) {
}
