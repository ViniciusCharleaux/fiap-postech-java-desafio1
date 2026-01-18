package br.com.fiap.tc.restaurant.application.usecase.cliente;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;

@Service
public class ExcluirCliente {

    private final ClienteRepositorio clienteRepositorio;

    public ExcluirCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public void execute(Long id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + id);
        }
        clienteRepositorio.deleteById(id);
    }
}
