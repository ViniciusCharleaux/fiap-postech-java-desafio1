package br.com.fiap.tc.restaurant.restaurante.domain.repository;

import java.util.Optional;
import java.util.List;

import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;

public interface ItemCardapioRepository {
    ItemCardapio save(ItemCardapio item);
    Optional<ItemCardapio> findById(Long id);
    List<ItemCardapio> findByRestauranteId(Long restauranteId);
    boolean existsById(Long id);
    void deleteById(Long id);
}
