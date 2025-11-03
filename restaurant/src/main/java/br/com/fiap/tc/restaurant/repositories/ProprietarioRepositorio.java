package br.com.fiap.tc.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.entities.ProprietarioRestaurante;

@Repository
public interface ProprietarioRepositorio extends JpaRepository<ProprietarioRestaurante, Long> {
    boolean existsByCnpj(String cnpj);
    List<ProprietarioRestaurante> findByNomeContainingIgnoreCase(String nome);
}

