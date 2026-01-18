package br.com.fiap.tc.restaurant.infrastructure.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.BuscaUsuariosDTO;
import br.com.fiap.tc.restaurant.application.dto.AtribuirTipoUsuarioDTO;
import br.com.fiap.tc.restaurant.application.dto.TrocaSenhaDTO;
import br.com.fiap.tc.restaurant.application.usecase.auth.BuscarUsuariosPorNome;
import br.com.fiap.tc.restaurant.application.usecase.auth.TrocarSenhaUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.AtribuirTipoAUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API de gerenciamento de usuários")
public class UsuariosController {

    private final BuscarUsuariosPorNome buscarUsuariosPorNome;
    private final TrocarSenhaUsuario trocarSenhaUsuario;
    private final AtribuirTipoAUsuario atribuirTipoAUsuario;

    public UsuariosController(BuscarUsuariosPorNome buscarUsuariosPorNome,
                              TrocarSenhaUsuario trocarSenhaUsuario,
                              AtribuirTipoAUsuario atribuirTipoAUsuario){
        this.buscarUsuariosPorNome = buscarUsuariosPorNome;
        this.trocarSenhaUsuario = trocarSenhaUsuario;
        this.atribuirTipoAUsuario = atribuirTipoAUsuario;
    }

    @GetMapping
    @Operation(summary = "Buscar usuários por nome", description = "Buscar usuários por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "E-mail, login ou CPF já cadastrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Page<BuscaUsuariosDTO>> buscarUsuarios(
            @RequestParam(required = false, defaultValue = "") String nome,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<BuscaUsuariosDTO> usuarios = buscarUsuariosPorNome.execute(nome, pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PatchMapping("/{login}/senha")
    @Operation(summary = "Trocar senha do usuário", description = "Valida a senha atual e atualiza para a nova senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> trocarSenha(
            @PathVariable String login,
            @Validated @RequestBody TrocaSenhaDTO dto
    ) {
        trocarSenhaUsuario.execute(login, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{usuarioId}/tipo-usuario")
    @Operation(summary = "Atribuir tipo de usuário", description = "Associa um tipo de usuário a um usuário existente")
    public ResponseEntity<Void> atribuirTipoUsuario(
            @PathVariable Long usuarioId,
            @Validated @RequestBody AtribuirTipoUsuarioDTO dto
    ) {
        atribuirTipoAUsuario.execute(usuarioId, dto);
        return ResponseEntity.noContent().build();
    }


}
