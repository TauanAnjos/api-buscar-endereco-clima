package com.tauan.viacep.buscar_edereco_clima.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDTO(String description) {
}
