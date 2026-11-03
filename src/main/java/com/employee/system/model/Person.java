package com.employee.system.model;

import java.util.Optional;

public class Person {
    AddressDto addressDto;

    public Person(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public Optional<AddressDto> getAddress() {
        return Optional.ofNullable(addressDto);
    }
}
