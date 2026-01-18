package br.com.fiap.tc.restaurant.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.domain.entities.Restaurante;

@Repository
public interface RestauranteRepositorio extends JpaRepository<Restaurante, Long> {
    
}
