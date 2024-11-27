package com.tauan.viacep.buscar_edereco_clima.rest.controllers;

import com.tauan.viacep.buscar_edereco_clima.rest.dtos.EnderecoDTO;
import com.tauan.viacep.buscar_edereco_clima.webService.WebServiceViaCep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cep")
public class EnderecoController {
    @Autowired
    private WebServiceViaCep  webServiceViaCep;

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable("cep")String cep) throws IOException, InterruptedException {
        return  ResponseEntity.ok(webServiceViaCep.buscadorCepEndereco(cep));
    }
}
