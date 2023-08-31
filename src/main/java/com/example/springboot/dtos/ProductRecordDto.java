package com.example.springboot.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {

	// records- no pueden crear setters, solamente getters
	// @NotBlank - nombre del producto no puede ser nula, ni en blanco en la base
	// datos
	// @NotNull - el valor no puede ser nulos en la base datos

}
