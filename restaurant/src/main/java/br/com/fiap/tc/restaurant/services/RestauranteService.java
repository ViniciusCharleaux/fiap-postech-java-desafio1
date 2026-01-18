package br.com.fiap.tc.restaurant.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.CriarRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@Service
public class RestauranteService {

    private final RestauranteRepositorio restauranteRepositorio;
    private final ProprietarioRepositorio proprietarioRepositorio;
    private final ConverteDTO converteDTO;

    @Autowired
    public RestauranteService(RestauranteRepositorio restauranteRepositorio, ProprietarioRepositorio proprietarioRepositorio, ConverteDTO converteDTO) {
        this.restauranteRepositorio = restauranteRepositorio;
        this.proprietarioRepositorio = proprietarioRepositorio;
        this.converteDTO = converteDTO;
    }

    public List<RestauranteResponseDTO> getAllRestaurantes() {
        return restauranteRepositorio.findAll()
                .stream()
                .map(converteDTO::converteParaRestauranteResponseDTO)
                .collect(Collectors.toList());
    }
    
    public RestauranteResponseDTO getRestauranteById(Long id) {
        Restaurante restaurante = restauranteRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        return converteDTO.converteParaRestauranteResponseDTO(restaurante);
    }
    
    public RestauranteResponseDTO createRestaurante(CriarRestauranteDTO dto) {
        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(dto.getProprietarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + dto.getProprietarioId()));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setEndereco(new Endereco(dto.getEndereco()));
        restaurante.setTipoDeCozinha(dto.getTipoDeCozinha());
        restaurante.setHorarioDeFuncionamento(dto.getHorarioDeFuncionamento());
        restaurante.setProprietario(proprietario);

        Restaurante salvo = restauranteRepositorio.save(restaurante);
        return converteDTO.converteParaRestauranteResponseDTO(salvo);
    }
    
    public RestauranteResponseDTO updateRestaurante(Long id, RestauranteUpdateDTO dto) {
        Restaurante restaurante = restauranteRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(dto.getProprietarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + dto.getProprietarioId()));

        restaurante.setNome(dto.getNome());
        restaurante.setEndereco(new Endereco(dto.getEndereco()));
        restaurante.setTipoDeCozinha(dto.getTipoDeCozinha());
        restaurante.setHorarioDeFuncionamento(dto.getHorarioDeFuncionamento());
        restaurante.setProprietario(proprietario);

        Restaurante atualizado = restauranteRepositorio.save(restaurante);
        return converteDTO.converteParaRestauranteResponseDTO(atualizado);
    }
    
    public void deleteRestaurante(Long id) {
        if (!restauranteRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("Restaurante não encontrado com ID: " + id);
        }
        restauranteRepositorio.deleteById(id);
    }
    
}
