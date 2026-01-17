package br.com.fiap.tc.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.entities.ItemCardapio;

@Repository
public interface ItemCardapioRepositorio extends JpaRepository<ItemCardapio, Long> {
    List<ItemCardapio> findByRestauranteId(Long restauranteId);
}
