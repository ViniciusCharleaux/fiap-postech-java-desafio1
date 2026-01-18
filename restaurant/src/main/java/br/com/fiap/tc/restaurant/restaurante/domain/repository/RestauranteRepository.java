package br.com.fiap.tc.restaurant.restaurante.domain.repository;

import java.util.Optional;
import java.util.List;

import br.com.fiap.tc.restaurant.domain.entities.Restaurante;

public interface RestauranteRepository {
    Restaurante save(Restaurante restaurante);
    Optional<Restaurante> findById(Long id);
    List<Restaurante> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}
