package com.tauan.viacep.buscar_edereco_clima.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MainDTO(Double temp, Double temp_min, Double temp_max, int humidity) {
}
