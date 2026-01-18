package br.com.fiap.tc.restaurant.application.usecase.cliente;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ClienteUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.Cliente;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@Service
public class AtualizarCliente {

    private final ClienteRepositorio clienteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ConverteDTO converteDto;

    public AtualizarCliente(ClienteRepositorio clienteRepositorio,
                            UsuarioRepositorio usuarioRepositorio,
                            ConverteDTO converteDto) {
        this.clienteRepositorio = clienteRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.converteDto = converteDto;
    }

    @Transactional
    public ClienteResponseDTO execute(Long id, ClienteUpdateDTO dto) {
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

        Cliente atualizado = clienteRepositorio.save(cliente);
        return converteDto.converteParaClienteResponseDTO(atualizado);
    }
}
