package com.example.Base58_practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
public class UpdateUserDto {

    @JsonProperty("firstName")
    private Optional<String> optionalFirstName = Optional.empty();

    @JsonProperty("lastName")
    private Optional<String> optionalLastName = Optional.empty();

    @JsonProperty("email")
    private Optional<String> optionalEmail = Optional.empty();

    @JsonProperty("phone")
    private Optional<String> optionalPhone = Optional.empty();


}
