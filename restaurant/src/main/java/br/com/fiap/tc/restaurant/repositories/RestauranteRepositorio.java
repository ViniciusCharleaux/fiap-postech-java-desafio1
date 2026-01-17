package br.com.fiap.tc.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tc.restaurant.entities.Restaurante;

@Repository
public interface RestauranteRepositorio extends JpaRepository<Restaurante, Long> {
    
}
