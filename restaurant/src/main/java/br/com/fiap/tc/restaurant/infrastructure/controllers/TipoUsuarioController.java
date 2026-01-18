package br.com.fiap.tc.restaurant.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioCreateDTO;
import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.AtualizarTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.CadastrarTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.ExcluirTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.ListarTiposUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
@Tag(name = "Tipos de Usuário", description = "CRUD de tipos de usuário")
public class TipoUsuarioController {

    private final CadastrarTipoUsuario cadastrarTipoUsuario;
    private final ListarTiposUsuario listarTiposUsuario;
    private final AtualizarTipoUsuario atualizarTipoUsuario;
    private final ExcluirTipoUsuario excluirTipoUsuario;

    public TipoUsuarioController(CadastrarTipoUsuario cadastrarTipoUsuario,
                                 ListarTiposUsuario listarTiposUsuario,
                                 AtualizarTipoUsuario atualizarTipoUsuario,
                                 ExcluirTipoUsuario excluirTipoUsuario) {
        this.cadastrarTipoUsuario = cadastrarTipoUsuario;
        this.listarTiposUsuario = listarTiposUsuario;
        this.atualizarTipoUsuario = atualizarTipoUsuario;
        this.excluirTipoUsuario = excluirTipoUsuario;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de usuário")
    public ResponseEntity<List<TipoUsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(listarTiposUsuario.execute());
        
    }

    @PostMapping
    @Operation(summary = "Cadastrar tipo de usuário")
    public ResponseEntity<TipoUsuarioResponseDTO> criar(@Valid @RequestBody TipoUsuarioCreateDTO dto) {
        TipoUsuarioResponseDTO criado = cadastrarTipoUsuario.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário")
    public ResponseEntity<TipoUsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                            @Valid @RequestBody TipoUsuarioCreateDTO dto) {
        TipoUsuarioResponseDTO atualizado = atualizarTipoUsuario.execute(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tipo de usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        excluirTipoUsuario.execute(id);
        return ResponseEntity.noContent().build();
    }
}
