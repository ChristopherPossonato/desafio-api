package com.api.desafio.dataprovider.repository;

import com.api.desafio.dataprovider.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findTop20ByNomeLikeOrSobrenomeLike(String nome, String sobrenome);
}
