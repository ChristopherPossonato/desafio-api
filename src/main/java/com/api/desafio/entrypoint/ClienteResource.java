package com.api.desafio.entrypoint;

import com.api.desafio.dto.ClienteDto;
import com.api.desafio.entrypoint.cliente.ClienteRequest;
import com.api.desafio.entrypoint.cliente.ClienteResponse;
import com.api.desafio.usecase.ClienteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/clientes")
    public ResponseEntity<Long> salvarCliente(@RequestBody @Valid ClienteRequest clienteRequest) {

        var clienteDto = mapper.map(clienteRequest, ClienteDto.class);

        Long clienteId = clienteService.salvar(clienteDto);

        return ResponseEntity.ok().body(clienteId);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDto>> listar() {
        var list = this.clienteService.listar();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> buscarId(@PathVariable Long id) {
        var obj = clienteService.buscarId(id);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/clientes/{id}")
    public void deletar(@PathVariable Long id) {
        clienteService.excluir(id);
    }

    @PutMapping("/clientes")
    public ResponseEntity<Long> atualizar(@RequestBody ClienteDto clienteDto) {
        var clienteAtualizado = clienteService.alterar(clienteDto);

       return ResponseEntity.ok().body(clienteAtualizado);
    }

    @GetMapping("/clientes/buscarnome/{nome}")
    public ResponseEntity<?> buscarNome(@PathVariable String nome) {
        List<ClienteResponse> clientesNome = clienteService.buscarNome(nome);

        return ResponseEntity.ok().body(clientesNome);
    }

}
