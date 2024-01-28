package com.api.desafio.entrypoint;

import com.api.desafio.dto.ClienteDto;
import com.api.desafio.entrypoint.model.cliente.ClienteRequest;
import com.api.desafio.entrypoint.model.cliente.ClienteResponse;
import com.api.desafio.entrypoint.model.cliente.EnderecoResponse;
import com.api.desafio.service.cliente.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Clientes", description = "Recursos para manipular clientes")
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listar() {
        var list = this.clienteService.listar();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca cliente por Id.")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        var obj = clienteService.buscarPorId(id).orElse(null);

        if (obj != null) {
            var ret = this.mapper.map(obj, ClienteResponse.class);

            //ModelMapper não está convertendo Record
            ret.setEndereco(EnderecoResponse
                    .builder()
                    .cep(obj.getEndereco().cep())
                    .logradouro(obj.getEndereco().logradouro())
                    .complemento(obj.getEndereco().complemento())
                    .bairro(obj.getEndereco().bairro())
                    .localidade(obj.getEndereco().localidade())
                    .uf(obj.getEndereco().uf())
                    .build());

            return ResponseEntity.ok().body(ret);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Salva novo cliente.")
    public ResponseEntity<Long> salvar(@RequestBody @Valid ClienteRequest clienteRequest) {

        var clienteDto = mapper.map(clienteRequest, ClienteDto.class);

        Long clienteId = clienteService.salvar(clienteDto);

        return ResponseEntity.ok().body(clienteId);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza cliente.")
    public ResponseEntity<Long> atualizar(@RequestBody ClienteRequest clienteRequest, @PathVariable("id") Long id) {
        var clienteDto = mapper.map(clienteRequest, ClienteDto.class);

        clienteDto.setId(id);

        var clienteAtualizado = clienteService.alterar(clienteDto);

        return ResponseEntity.ok().body(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta cliente.")
    public void deletar(@PathVariable Long id) {
        clienteService.excluir(id);
    }

    @GetMapping("/buscarnome")
    @ApiOperation(value = "Filtra cliente por nome.")
    public ResponseEntity<List<ClienteResponse>> buscarNome(@RequestParam(name = "nome", required = false) String nome) {
        var clienteList = clienteService.buscarPorNome(nome);

        List<ClienteResponse> response = new ArrayList<>();

        clienteList.stream().forEach(c -> {
            response.add(this.mapper.map(c, ClienteResponse.class));
        });

        return ResponseEntity.ok().body(response);
    }
}
