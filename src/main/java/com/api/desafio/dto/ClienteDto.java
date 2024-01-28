package com.api.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto implements Serializable {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cep;
    private EnderecoDto endereco;


}