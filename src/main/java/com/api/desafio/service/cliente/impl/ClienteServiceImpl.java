package com.api.desafio.service.cliente.impl;

import com.api.desafio.adapter.viacep.impl.ConsultaCepAdapterImpl;
import com.api.desafio.dataprovider.model.Cliente;
import com.api.desafio.dataprovider.repository.ClienteRepository;
import com.api.desafio.dto.ClienteDto;
import com.api.desafio.dto.EnderecoDto;
import com.api.desafio.service.cliente.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    public List<ClienteDto> listar() {
        var list = new ArrayList<ClienteDto>();

        this.clienteRepository.findAll().forEach(o -> {
            list.add(this.mapper.map(o, ClienteDto.class));
        });

        return list.stream()
                .sorted(Comparator.comparing(ClienteDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDto> buscarPorId(Long id) {
        var cliente = clienteRepository.findById(id).orElse(null);

        if (cliente != null) {
            EnderecoDto endereco = consultaCepAdapter.execute(cliente.getCep());
            var end = EnderecoDto.createDefaultIfNullOrEmpty(
                    endereco.cep(),
                    endereco.logradouro(),
                    endereco.complemento(),
                    endereco.bairro(),
                    endereco.localidade(),
                    endereco.uf());

            ClienteDto obj = null;
            obj = mapper.map(cliente, ClienteDto.class);

            obj.setEndereco(end);

            return Optional.of(obj);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Long salvar(ClienteDto clienteDto){

        var obj = this.mapper.map(clienteDto, Cliente.class);

        var clienteSalvo = clienteRepository.save(obj);

        return clienteSalvo.getId();
    }

    @Override
    public Long alterar(ClienteDto clienteDto) {

        if (clienteDto.getId() == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo para atualização");
        }

        var clienteExistente = mapper.map(clienteDto, Cliente.class);
        clienteRepository.findById(clienteExistente.getId()).orElseThrow();

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

         return clienteAtualizado.getId();
    }

    @Override
    public void excluir(Long id) {
        var obj = clienteRepository.findById(id);
        obj.ifPresent(cliente -> clienteRepository.delete(cliente));
    }

    @Override
    public EnderecoDto obterEnderecoPorCep(String cep) {
        try {
            return consultaCepAdapter.execute(cep);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter endereço por CEP", e);
        }
    }

    @Override
    public List<ClienteDto> buscarPorNome(String nome) {

        List<Cliente> clienteList = clienteRepository.findTop20ByNomeLikeOrSobrenomeLike("%" + nome + "%", "%" + nome + "%");
        var  listaFiltrada = clienteList.stream()
                .sorted(Comparator.comparing(Cliente::getNome))
                .map(o -> mapper.map(o, ClienteDto.class))
                .collect(Collectors.toList());

        return listaFiltrada;
    }
}
