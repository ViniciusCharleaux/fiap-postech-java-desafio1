package br.com.fiap.tc.restaurant.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.entities.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    Optional<Usuario> findByLogin(String login);
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
