package br.com.fiap.tc.restaurant.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.dto.ProprietarioUpdateDTO;
import br.com.fiap.tc.restaurant.entities.Endereco;
import br.com.fiap.tc.restaurant.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.repositories.UsuarioRepositorio;
import jakarta.transaction.Transactional;

@Service
public class ProprietarioRestauranteService {

    private final ProprietarioRepositorio proprietarioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ConverteDTO converteDto = new ConverteDTO();
    private final PasswordEncoder passwordEncoder;
     ConverteDTO converteDTO = new ConverteDTO();

    public ProprietarioRestauranteService(
        ProprietarioRepositorio proprietarioRepositorio, 
        UsuarioRepositorio usuarioRepositorio, 
        ConverteDTO converteDto, 
        PasswordEncoder passwordEncoder
        ) {
            this.proprietarioRepositorio = proprietarioRepositorio;
            this.usuarioRepositorio = usuarioRepositorio;
            this.passwordEncoder = passwordEncoder;
    }

    public ProprietarioResponseDTO cadastrarProprietario(CriarProprietarioRestauranteDTO dto) {

        if (usuarioRepositorio.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("E-mail já cadastrado: " + dto.getEmail());
        }

        if (usuarioRepositorio.existsByLogin(dto.getLogin())){
            throw new DuplicateResourceException("Login ja cadastrado: " + dto.getLogin());
        }

        if (proprietarioRepositorio.existsByCnpj(dto.getCnpj())){
            throw new DuplicateResourceException("CNPJ ja cadastrado: " + dto.getCnpj());
        }

        ProprietarioRestaurante proprietario = new ProprietarioRestaurante();
        proprietario.setNome(dto.getNome());
        proprietario.setEmail(dto.getEmail());
        proprietario.setLogin(dto.getLogin());
        proprietario.setSenha(passwordEncoder.encode(dto.getSenha()));
        Endereco endereco = new Endereco(dto.getEndereco());
        proprietario.setEndereco(endereco);
        proprietario.setSenha(passwordEncoder.encode(dto.getSenha()));
        proprietario.setCnpj(dto.getCnpj());
        proprietario.setTelefoneComercial(dto.getTelefoneComercial());
        proprietario.setDataCriacao(LocalDateTime.now());
        proprietario.setDataUltimaAlteracao(LocalDateTime.now());
        proprietario.setNomeRestaurante(dto.getNomeRestaurante());
        proprietario.setUserType("PROPRIETARIO");


        ProprietarioRestaurante novoProprietario = proprietarioRepositorio.save(proprietario);
        return converteDto.convertePaProprietarioResponseDTO(novoProprietario);
    }

    public void excluirProprietario(Long id) {

        if(!proprietarioRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Proprietario não encontrado: " + id);
        }

        proprietarioRepositorio.deleteById(id);
    }

    @Transactional
    public ProprietarioResponseDTO atualizarProprietario(Long id, ProprietarioUpdateDTO dto) {
        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + id));

        Endereco endereco = new Endereco(dto.getEndereco());

        proprietario.setNomeRestaurante(dto.getNomeRestaurante());
        proprietario.setTelefoneComercial(dto.getTelefoneComercial());
        proprietario.setEmail(dto.getEmail());
        proprietario.setCnpj(dto.getCnpj());
        proprietario.setEndereco(endereco);
        proprietario.setDataUltimaAlteracao(LocalDateTime.now());
        proprietario.setNome(dto.getNome());

        ProprietarioRestaurante updatedProprietario = proprietarioRepositorio.save(proprietario);
        return converteDTO.convertePaProprietarioResponseDTO(updatedProprietario);
    }
}
