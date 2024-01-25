package com.api.desafio.adapter;

import com.api.desafio.dto.Endereco;

public interface ConsultaCepAdapter {
    Endereco execute(String cep);
}
