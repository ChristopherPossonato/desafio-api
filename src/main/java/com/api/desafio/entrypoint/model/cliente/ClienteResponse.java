package com.api.desafio.entrypoint.model.cliente;

import com.api.desafio.dto.EnderecoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.OptionalDouble;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;

    private EnderecoResponse endereco;


}
