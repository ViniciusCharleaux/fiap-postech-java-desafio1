package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.CriarRestauranteDTO;
import br.com.fiap.tc.restaurant.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.entities.Endereco;
import br.com.fiap.tc.restaurant.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.entities.Restaurante;
import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.RestauranteRepository;

@Service
public class CadastrarRestaurante {

    private final RestauranteRepository restauranteRepository;
    private final ProprietarioRepositorio proprietarioRepositorio;
    private final ConverteDTO converteDTO;

    public CadastrarRestaurante(RestauranteRepository restauranteRepository,
                                ProprietarioRepositorio proprietarioRepositorio,
                                ConverteDTO converteDTO) {
        this.restauranteRepository = restauranteRepository;
        this.proprietarioRepositorio = proprietarioRepositorio;
        this.converteDTO = converteDTO;
    }

    public RestauranteResponseDTO execute(CriarRestauranteDTO dto) {
        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(dto.getProprietarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + dto.getProprietarioId()));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setEndereco(new Endereco(dto.getEndereco()));
        restaurante.setTipoDeCozinha(dto.getTipoDeCozinha());
        restaurante.setHorarioDeFuncionamento(dto.getHorarioDeFuncionamento());
        restaurante.setProprietario(proprietario);

        Restaurante salvo = restauranteRepository.save(restaurante);
        return converteDTO.converteParaRestauranteResponseDTO(salvo);
    }
}
