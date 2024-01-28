package com.api.desafio.adapter.viacep.impl;

import com.api.desafio.adapter.viacep.ConsultaCepAdapter;
import com.api.desafio.dto.EnderecoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsultaCepAdapterImpl implements ConsultaCepAdapter {
    @Value("${viacep.url}")
    private String viaCepUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public EnderecoDto execute(String cep) {
        var url = viaCepUrl + cep + "/json/";

        try {
            return restTemplate.getForObject(url, EnderecoDto.class);
        } catch (Exception e) {

            return null;
        }
    }
}
