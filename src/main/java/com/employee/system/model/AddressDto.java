package com.employee.system.model;

import java.util.Optional;

public class AddressDto {
    String street;

    public AddressDto(String street) {
        this.street = street;
    }

    public Optional<String> getStreet() {
        return Optional.ofNullable(street);
    }
}
