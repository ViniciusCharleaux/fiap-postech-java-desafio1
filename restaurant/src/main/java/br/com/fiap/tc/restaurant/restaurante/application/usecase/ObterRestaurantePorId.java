package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.entities.Restaurante;
import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.RestauranteRepository;

@Service
public class ObterRestaurantePorId {

    private final RestauranteRepository restauranteRepository;
    private final ConverteDTO converteDTO;

    public ObterRestaurantePorId(RestauranteRepository restauranteRepository, ConverteDTO converteDTO) {
        this.restauranteRepository = restauranteRepository;
        this.converteDTO = converteDTO;
    }

    public RestauranteResponseDTO execute(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        return converteDTO.converteParaRestauranteResponseDTO(restaurante);
    }
}
