package com.api.desafio.usecase;

import com.api.desafio.dto.ClienteDto;
import com.api.desafio.dto.Endereco;
import com.api.desafio.entrypoint.cliente.ClienteResponse;


import java.util.List;

public interface ClienteService {
    void excluir(Long id);
    List<ClienteDto> listar();
    Long salvar(ClienteDto clienteDto);
    Long alterar(ClienteDto clienteDto);
    Endereco obterEnderecoPorCep(String cep);
    ClienteResponse buscarId(Long id);
    List<ClienteResponse> buscarNome(String nome);
}
