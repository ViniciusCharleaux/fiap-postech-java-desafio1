package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@Service
public class CadastrarProprietario {

    private final ProprietarioRepositorio proprietarioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ConverteDTO converteDto;
    private final PasswordEncoder passwordEncoder;

    public CadastrarProprietario(ProprietarioRepositorio proprietarioRepositorio,
                                 UsuarioRepositorio usuarioRepositorio,
                                 ConverteDTO converteDto,
                                 PasswordEncoder passwordEncoder) {
        this.proprietarioRepositorio = proprietarioRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.converteDto = converteDto;
        this.passwordEncoder = passwordEncoder;
    }

    public ProprietarioResponseDTO execute(CriarProprietarioRestauranteDTO dto) {
        if (usuarioRepositorio.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("E-mail já cadastrado: " + dto.getEmail());
        }
        if (usuarioRepositorio.existsByLogin(dto.getLogin())) {
            throw new DuplicateResourceException("Login ja cadastrado: " + dto.getLogin());
        }
        if (proprietarioRepositorio.existsByCnpj(dto.getCnpj())) {
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
}
