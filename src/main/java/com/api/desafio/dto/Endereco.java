package com.api.desafio.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Endereco(
        @JsonAlias("cep") String cep,
        @JsonAlias("logradouro") String logradouro,
        @JsonAlias("complemento") String complemento,
        @JsonAlias("bairro") String bairro,
        @JsonAlias("localidade") String localidade,
        @JsonAlias("uf") String uf
){
}
