package br.com.fiap.tc.restaurant.controllers;

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

import br.com.fiap.tc.restaurant.dto.BuscaUsuariosDTO;
import br.com.fiap.tc.restaurant.dto.TrocaSenhaDTO;
import br.com.fiap.tc.restaurant.services.UsuarioService;
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

    private final UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
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
        Page<BuscaUsuariosDTO> usuarios = usuarioService.buscarUsuariosPorNome(nome, pageable);
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
        usuarioService.trocarSenha(login, dto);
        return ResponseEntity.noContent().build();
    }


}
