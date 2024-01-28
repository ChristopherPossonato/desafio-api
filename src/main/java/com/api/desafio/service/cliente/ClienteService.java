package com.api.desafio.service.cliente;

import com.api.desafio.dto.ClienteDto;
import com.api.desafio.dto.EnderecoDto;
import com.api.desafio.entrypoint.model.cliente.ClienteResponse;


import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<ClienteDto> listar();
    Optional<ClienteDto> buscarPorId(Long id);
    Long salvar(ClienteDto clienteDto);
    Long alterar(ClienteDto clienteDto);
    void excluir(Long id);
    EnderecoDto obterEnderecoPorCep(String cep);
    List<ClienteDto> buscarPorNome(String nome);
}
