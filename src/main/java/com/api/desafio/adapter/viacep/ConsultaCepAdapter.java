package com.api.desafio.adapter.viacep;

import com.api.desafio.dto.EnderecoDto;

public interface ConsultaCepAdapter {
    EnderecoDto execute(String cep);
}
