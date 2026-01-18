package br.com.fiap.tc.restaurant.infrastructure.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.repositories.ItemCardapioRepositorio;

@Repository
public class ItemCardapioRepositoryJpaAdapter implements ItemCardapioRepository {

    private final ItemCardapioRepositorio jpa;

    public ItemCardapioRepositoryJpaAdapter(ItemCardapioRepositorio jpa) {
        this.jpa = jpa;
    }

    @Override
    public ItemCardapio save(ItemCardapio item) {
        return jpa.save(item);
    }

    @Override
    public Optional<ItemCardapio> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<ItemCardapio> findByRestauranteId(Long restauranteId) {
        return jpa.findByRestauranteId(restauranteId);
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
