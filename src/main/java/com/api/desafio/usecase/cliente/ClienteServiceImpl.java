package com.api.desafio.usecase.cliente;

import com.api.desafio.adapter.viacep.ConsultaCepAdapterImpl;
import com.api.desafio.dto.ClienteDto;
import com.api.desafio.dataprovider.model.Cliente;
import com.api.desafio.dto.Endereco;
import com.api.desafio.entrypoint.cliente.ClienteResponse;
import com.api.desafio.dataprovider.repository.ClienteRepository;
import com.api.desafio.usecase.ClienteService;
import com.api.desafio.utilsApi.MaskTelefone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConsultaCepAdapterImpl consultaCepAdapter;
    public ClienteServiceImpl(ClienteRepository clienteRepository, ConsultaCepAdapterImpl consultaCepAdapter) {
        this.clienteRepository = clienteRepository;
        this.consultaCepAdapter = consultaCepAdapter;
    }
    @Override
    public void excluir(Long id) {
        var obj = clienteRepository.findById(id);
        obj.ifPresent(cliente -> clienteRepository.delete(cliente));
    }

    @Override
    public List<ClienteDto> listar() {
        var list = new ArrayList<ClienteDto>();

        this.clienteRepository.findAll().forEach(o -> {
            list.add(this.mapper.map(o, ClienteDto.class));
        });

        return list.stream()
                .sorted(Comparator.comparing(ClienteDto::getId))
                .collect(Collectors.toList());

    }

    public Endereco obterEnderecoPorCep(String cep) {
        try {
            return consultaCepAdapter.execute(cep);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter endereço por CEP", e);
        }
    }

    @Override
    public ClienteResponse buscarId(Long id) {
        var clienteExistente = clienteRepository.findById(id);
        var cep = clienteExistente.get().getCep();
        Endereco endereco = consultaCepAdapter.execute(cep);
        ClienteResponse obj = null;

        if (clienteExistente.isPresent()) {
            obj = mapper.map(clienteExistente, ClienteResponse.class);
        }
        var tel = MaskTelefone.format(obj.getTelefone());
        obj.setTelefone(tel);
        obj.setEndereco(endereco);

        return obj;
    }

    @Override
    public List<ClienteResponse> buscarNome(String nome) {

        List<Cliente> clienteList = clienteRepository.findAll();
        var  listaFiltrada = clienteList.stream()
                .filter(o -> o.getNome().toUpperCase().contains(nome.toUpperCase()))
                .sorted(Comparator.comparing(Cliente::getNome))
                .map(o -> mapper.map(o, ClienteResponse.class))
                .collect(Collectors.toList());

        return listaFiltrada;
    }

    @Override
    public Long salvar(ClienteDto clienteDto){

        var obj = this.mapper.map(clienteDto, Cliente.class);

        var clienteSalvo = clienteRepository.save(obj);

        return clienteSalvo.getId();
    }


    public Long alterar(ClienteDto clienteDto) {

        if (clienteDto.getId() == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo para atualização");
        }
        var clienteExistente = mapper.map(clienteDto, Cliente.class);
        clienteRepository.findById(clienteExistente.getId()).orElseThrow();

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

         return clienteAtualizado.getId();
    }


}
