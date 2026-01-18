package br.com.fiap.tc.restaurant.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;

@Repository
public interface ItemCardapioRepositorio extends JpaRepository<ItemCardapio, Long> {
    List<ItemCardapio> findByRestauranteId(Long restauranteId);
}
