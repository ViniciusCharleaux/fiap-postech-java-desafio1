package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.RestauranteRepository;

@Service
public class ListarRestaurantes {

    private final RestauranteRepository restauranteRepository;
    private final ConverteDTO converteDTO;

    public ListarRestaurantes(RestauranteRepository restauranteRepository, ConverteDTO converteDTO) {
        this.restauranteRepository = restauranteRepository;
        this.converteDTO = converteDTO;
    }

    public List<RestauranteResponseDTO> execute() {
        return restauranteRepository.findAll()
                .stream()
                .map(converteDTO::converteParaRestauranteResponseDTO)
                .collect(Collectors.toList());
    }
}
