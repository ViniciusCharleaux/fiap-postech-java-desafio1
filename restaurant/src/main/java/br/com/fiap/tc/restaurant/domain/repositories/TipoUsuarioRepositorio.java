package br.com.fiap.tc.restaurant.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;

public interface TipoUsuarioRepositorio extends JpaRepository<TipoUsuario, Long> {
    boolean existsByNome(String nome);
    Optional<TipoUsuario> findByNome(String nome);
}
