package br.com.fiap.tc.restaurant.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}

