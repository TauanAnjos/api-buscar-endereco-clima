package com.tauan.viacep.buscar_edereco_clima.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClimaDTOResponse(Double temperatura,
                               Double temperatura_minima,
                               Double temperatura_maxima,
                               int umidade,
                               String descricao) {
}
