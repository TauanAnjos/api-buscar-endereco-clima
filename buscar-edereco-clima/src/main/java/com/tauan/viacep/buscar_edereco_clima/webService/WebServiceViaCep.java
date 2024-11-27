package com.tauan.viacep.buscar_edereco_clima.webService;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tauan.viacep.buscar_edereco_clima.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WebServiceViaCep {
    @Value("${viacep.api.url}")
    private String viacepURL;
    @Value("${geoloca.api.url}")
    private String geolocaURL;
    @Value("${weather.api.url}")
    private String climaURL;
    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;

    public EnderecoDTO buscadorCepEndereco(String cep) throws IOException, InterruptedException {
        String url = viacepURL + cep + "/json/";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            EnderecoDTO endereco = objectMapper.readValue(response.body(), EnderecoDTO.class);

            CoordenadasDTO cordenadas = buscarLatAndLong(endereco);
            ClimaDTO clima = buscarClima(cordenadas);
            ClimaDTOResponse climaResponse = new ClimaDTOResponse(clima.main().temp(), clima.main().temp_min(), clima.main().temp_max(), clima.main().humidity(), clima.weather().get(0).description());
            endereco = new EnderecoDTO(endereco.cep(), endereco.logradouro(), endereco.complemento(), endereco.bairro(), endereco.localidade(), endereco.uf(), climaResponse);
            return endereco;
        } else {
            System.out.println("Erro ao consumir a API. Status code: " + response.statusCode());
        }
        return  null;
    }

    public CoordenadasDTO buscarLatAndLong(EnderecoDTO enderecoDTO) throws IOException, InterruptedException {
        String localidade = URLEncoder.encode(enderecoDTO.localidade(), StandardCharsets.UTF_8);
        String uf = URLEncoder.encode(enderecoDTO.uf(), StandardCharsets.UTF_8);
        String url = geolocaURL + localidade + "," + uf + ",BR&appid=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();
            List<CoordenadasDTO> coordenadasList = objectMapper.readValue(
                    response.body(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CoordenadasDTO.class)
            );

            if (!coordenadasList.isEmpty()) {
                return coordenadasList.get(0);
            }
        }
        return null;
    }
    public ClimaDTO buscarClima(CoordenadasDTO coordenadas) throws IOException, InterruptedException {
        String url = climaURL + "?lat=" + coordenadas.latitude() + "&lon=" + coordenadas.longitude() + "&appid=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();
            ClimaDTO clima = objectMapper.readValue( response.body(), ClimaDTO.class);

            if(clima != null){

                double temperatura = clima.main().temp() - 273.15;
                double temp_minima = clima.main().temp_min() - 273.15;
                double temp_max = clima.main().temp_max() - 273.15;
                return new ClimaDTO(new MainDTO(temperatura, temp_minima, temp_max,clima.main().humidity()),clima.weather());
            }
        }
        return null;
    }
}
