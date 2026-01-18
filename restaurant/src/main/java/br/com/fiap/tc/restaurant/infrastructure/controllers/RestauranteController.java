package br.com.fiap.tc.restaurant.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.CriarRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.CadastrarRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.ListarRestaurantes;
import br.com.fiap.tc.restaurant.application.usecase.ObterRestaurantePorId;
import br.com.fiap.tc.restaurant.application.usecase.AtualizarRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.ExcluirRestaurante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/restaurantes")
@Tag(name = "Restaurantes", description = "API de gerenciamento de restaurantes")
public class RestauranteController {

    private final CadastrarRestaurante cadastrarRestaurante;
    private final ListarRestaurantes listarRestaurantes;
    private final ObterRestaurantePorId obterRestaurantePorId;
    private final AtualizarRestaurante atualizarRestaurante;
    private final ExcluirRestaurante excluirRestaurante;

    public RestauranteController(CadastrarRestaurante cadastrarRestaurante,
                                 ListarRestaurantes listarRestaurantes,
                                 ObterRestaurantePorId obterRestaurantePorId,
                                 AtualizarRestaurante atualizarRestaurante,
                                 ExcluirRestaurante excluirRestaurante) {
        this.cadastrarRestaurante = cadastrarRestaurante;
        this.listarRestaurantes = listarRestaurantes;
        this.obterRestaurantePorId = obterRestaurantePorId;
        this.atualizarRestaurante = atualizarRestaurante;
        this.excluirRestaurante = excluirRestaurante;
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Lista todos os restaurantes")
    public ResponseEntity<List<RestauranteResponseDTO>> listar() {
        return ResponseEntity.ok(listarRestaurantes.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna um restaurante pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = obterRestaurantePorId.execute(id);
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    @Operation(summary = "Cadastrar restaurante", description = "Cria um novo restaurante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<RestauranteResponseDTO> criar(@Valid @RequestBody CriarRestauranteDTO dto) {
        RestauranteResponseDTO criado = cadastrarRestaurante.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante", description = "Atualiza os dados do restaurante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante ou proprietário não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<RestauranteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RestauranteUpdateDTO dto) {
        RestauranteResponseDTO atualizado = atualizarRestaurante.execute(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir restaurante", description = "Remove um restaurante do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        excluirRestaurante.execute(id);
        return ResponseEntity.noContent().build();
    }
}
