package com.api.desafio.adapter.viacep;

import com.api.desafio.adapter.ConsultaCepAdapter;
import com.api.desafio.dto.Endereco;
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
    public Endereco execute(String cep) {
        var url = viaCepUrl + cep + "/json/";

        try {
            return restTemplate.getForObject(url, Endereco.class);
        } catch (Exception e) {

            return null;
        }
    }
}
