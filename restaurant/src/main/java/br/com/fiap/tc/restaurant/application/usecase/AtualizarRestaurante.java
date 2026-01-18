package br.com.fiap.tc.restaurant.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.RestauranteRepository;

@Service
public class AtualizarRestaurante {

    private final RestauranteRepository restauranteRepository;
    private final ProprietarioRepositorio proprietarioRepositorio;
    private final ConverteDTO converteDTO;

    public AtualizarRestaurante(RestauranteRepository restauranteRepository,
                                ProprietarioRepositorio proprietarioRepositorio,
                                ConverteDTO converteDTO) {
        this.restauranteRepository = restauranteRepository;
        this.proprietarioRepositorio = proprietarioRepositorio;
        this.converteDTO = converteDTO;
    }

    public RestauranteResponseDTO execute(Long id, RestauranteUpdateDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(dto.getProprietarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + dto.getProprietarioId()));

        restaurante.setNome(dto.getNome());
        restaurante.setEndereco(new Endereco(dto.getEndereco()));
        restaurante.setTipoDeCozinha(dto.getTipoDeCozinha());
        restaurante.setHorarioDeFuncionamento(dto.getHorarioDeFuncionamento());
        restaurante.setProprietario(proprietario);

        Restaurante atualizado = restauranteRepository.save(restaurante);
        return converteDTO.converteParaRestauranteResponseDTO(atualizado);
    }
}
