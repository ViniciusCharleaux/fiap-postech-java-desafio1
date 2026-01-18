package br.com.fiap.tc.restaurant.infrastructure.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@Repository
public class RestauranteRepositoryJpaAdapter implements RestauranteRepository {

    private final RestauranteRepositorio jpa;

    public RestauranteRepositoryJpaAdapter(RestauranteRepositorio jpa) {
        this.jpa = jpa;
    }

    @Override
    public Restaurante save(Restaurante restaurante) {
        return jpa.save(restaurante);
    }

    @Override
    public Optional<Restaurante> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<Restaurante> findAll() {
        return jpa.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return jpa.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
