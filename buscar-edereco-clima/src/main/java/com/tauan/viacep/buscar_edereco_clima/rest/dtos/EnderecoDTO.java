package com.tauan.viacep.buscar_edereco_clima.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoDTO(String cep,
                          String logradouro,
                          String complemento,
                          String bairro,
                          String localidade,
                          String uf,
                          ClimaDTOResponse clima) {
}
