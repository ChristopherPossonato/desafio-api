package com.api.desafio.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoDto(
        @JsonAlias("cep") String cep,
        @JsonAlias("logradouro") String logradouro,
        @JsonAlias("complemento") String complemento,
        @JsonAlias("bairro") String bairro,
        @JsonAlias("localidade") String localidade,
        @JsonAlias("uf") String uf
){

    public static EnderecoDto createDefaultIfNullOrEmpty(String cep, String logradouro, String complemento, String bairro, String localidade, String uf) {
        return new EnderecoDto(
                cep.isEmpty() ? "N/I" : cep,
                logradouro.isEmpty() ? "N/I" : logradouro,
                complemento.isEmpty() ? "N/I" : complemento,
                bairro.isEmpty() ? "N/I" : bairro,
                localidade.isEmpty() ? "N/I" : localidade,
                uf.isEmpty() ? "N/I" : uf
        );
    }
}
