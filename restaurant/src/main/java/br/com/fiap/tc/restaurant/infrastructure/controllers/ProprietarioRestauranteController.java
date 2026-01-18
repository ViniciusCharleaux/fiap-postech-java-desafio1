package br.com.fiap.tc.restaurant.infrastructure.controllers;

import br.com.fiap.tc.restaurant.application.usecase.proprietario.AtualizarProprietario;
import br.com.fiap.tc.restaurant.application.usecase.proprietario.CadastrarProprietario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.proprietario.ExcluirProprietario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/proprietarios")
@Tag(name = "Proprietarios", description = "API de gerenciamento de proprietarios de restaurantes")
public class ProprietarioRestauranteController {
    private final CadastrarProprietario cadastrarProprietario;
    private final ExcluirProprietario excluirProprietario;
    private final AtualizarProprietario atualizarProprietario;

    public ProprietarioRestauranteController(CadastrarProprietario cadastrarProprietario,
                                             ExcluirProprietario excluirProprietario,
                                             AtualizarProprietario atualizarProprietario) {
        this.cadastrarProprietario = cadastrarProprietario;
        this.excluirProprietario = excluirProprietario;
        this.atualizarProprietario = atualizarProprietario;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "E-mail, login ou CPF já cadastrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ProprietarioResponseDTO> cadastrarProprietario(@Valid @RequestBody CriarProprietarioRestauranteDTO dto) {
        ProprietarioResponseDTO cliente = cadastrarProprietario.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir proprietário", description = "Remove um proprietário do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proprietário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        excluirProprietario.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do proprietário", 
        description = "Atualiza nome, email, telefone comercial, CNPJ, nome do restaurante e endereço (não inclui senha)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "409", description = "E-mail ou CNPJ já cadastrado para outro proprietário",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ProprietarioResponseDTO> atualizarProprietario(
            @PathVariable Long id,
            @Valid @RequestBody ProprietarioUpdateDTO dto) {
        ProprietarioResponseDTO proprietario = atualizarProprietario.execute(id, dto);
        return ResponseEntity.ok(proprietario);
    }
}
