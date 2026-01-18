package br.com.fiap.tc.restaurant.domain.repositories;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    Optional<Usuario> findByLogin(String login);
    Optional<Usuario> findByEmail(String login);
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
