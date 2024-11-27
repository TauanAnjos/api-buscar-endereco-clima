Buscador de Endereço e Clima
Este projeto é uma API RESTful desenvolvida em Spring Boot que permite buscar informações sobre um endereço e o clima atual, utilizando o CEP fornecido. Ele integra três APIs externas para:
Buscar o endereço: A partir do CEP, é feito um chamado à API do ViaCEP para retornar informações de endereço.
Obter coordenadas geográficas: Utiliza a API de Geolocalização para obter latitude e longitude com base na localidade e UF.
Consultar o clima: Com as coordenadas geográficas, a API do OpenWeather é chamada para retornar as condições climáticas atuais.

Funcionalidades
Buscar o endereço completo (logradouro, bairro, cidade, etc.) usando um CEP.
Obter as coordenadas geográficas (latitude e longitude) do endereço.
Exibir informações detalhadas do clima, como temperatura, umidade e descrição do tempo.

Tecnologias Utilizadas
Spring Boot: Para criar a API RESTful.
ViaCEP API: Para buscar os dados do endereço.
OpenWeather API: Para buscar as condições climáticas.
Geolocalização API: Para buscar as coordenadas geográficas.
