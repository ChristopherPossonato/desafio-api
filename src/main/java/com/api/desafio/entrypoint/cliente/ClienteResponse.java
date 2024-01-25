package com.api.desafio.entrypoint.cliente;

import com.api.desafio.dto.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Endereco endereco;


}
