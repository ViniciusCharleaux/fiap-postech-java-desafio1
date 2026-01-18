package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;

@Service
public class ExcluirProprietario {

    private final ProprietarioRepositorio proprietarioRepositorio;

    public ExcluirProprietario(ProprietarioRepositorio proprietarioRepositorio) {
        this.proprietarioRepositorio = proprietarioRepositorio;
    }

    public void execute(Long id) {
        if (!proprietarioRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Proprietario não encontrado: " + id);
        }
        proprietarioRepositorio.deleteById(id);
    }
}
