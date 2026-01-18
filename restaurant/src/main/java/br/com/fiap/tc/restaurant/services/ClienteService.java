package br.com.fiap.tc.restaurant.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.application.dto.ClienteUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.Cliente;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ConverteDTO converteDto = new ConverteDTO();
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
        ClienteRepositorio clienteRepositorio, 
        UsuarioRepositorio usuarioRepositorio, 
        ConverteDTO converteDto, 
        PasswordEncoder passwordEncoder
        ) {
            this.clienteRepositorio = clienteRepositorio;
            this.usuarioRepositorio = usuarioRepositorio;
            this.passwordEncoder = passwordEncoder;
    }

    public ClienteResponseDTO cadastrarCliente(CriarClienteDTO dto) {

        if (usuarioRepositorio.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("E-mail já cadastrado: " + dto.getEmail());
        }

        if (clienteRepositorio.existsByCpf(dto.getCpf())) {
            throw new DuplicateResourceException("CPF já cadastrado: " + dto.getCpf());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setLogin(dto.getLogin());
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        Endereco endereco = new Endereco(dto.getEndereco());
        cliente.setEndereco(endereco);
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setDataCriacao(LocalDateTime.now());
        cliente.setDataUltimaAlteracao(LocalDateTime.now());
        cliente.setUserType("CLIENTE");
        


        Cliente savedCliente = clienteRepositorio.save(cliente);
        return converteDto.converteParaClienteResponseDTO(savedCliente);
    }

    public void excluirCliente(Long id) {

        if(!clienteRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + id);
        }

        clienteRepositorio.deleteById(id);
    }
    
    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));

        if (!cliente.getEmail().equalsIgnoreCase(dto.getEmail())) {
            usuarioRepositorio.findByEmail(dto.getEmail()).ifPresent(c -> {
                throw new RuntimeException("Já existe um cliente com o e-mail informado.");
            });
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());

        Endereco endereco = new Endereco(dto.getEndereco());
        cliente.setEndereco(endereco);

        cliente.setDataUltimaAlteracao(LocalDateTime.now());

        Cliente atualizado = clienteRepositorio.save(cliente);
        return converteDto.converteParaClienteResponseDTO(atualizado);
    }
    
}
