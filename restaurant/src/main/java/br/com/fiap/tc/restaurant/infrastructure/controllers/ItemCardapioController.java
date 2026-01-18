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

import br.com.fiap.tc.restaurant.application.dto.CriarItemCardapioDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.CadastrarItemCardapio;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ListarItensPorRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ObterItemCardapioPorId;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.AtualizarItemCardapio;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ExcluirItemCardapio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/itens-cardapio")
@Tag(name = "Itens do Cardápio", description = "API de gerenciamento de itens vendidos no cardápio")
public class ItemCardapioController {

    private final CadastrarItemCardapio cadastrarItemCardapio;
    private final ListarItensPorRestaurante listarItensPorRestaurante;
    private final ObterItemCardapioPorId obterItemCardapioPorId;
    private final AtualizarItemCardapio atualizarItemCardapio;
    private final ExcluirItemCardapio excluirItemCardapio;

    public ItemCardapioController(CadastrarItemCardapio cadastrarItemCardapio,
                                  ListarItensPorRestaurante listarItensPorRestaurante,
                                  ObterItemCardapioPorId obterItemCardapioPorId,
                                  AtualizarItemCardapio atualizarItemCardapio,
                                  ExcluirItemCardapio excluirItemCardapio) {
        this.cadastrarItemCardapio = cadastrarItemCardapio;
        this.listarItensPorRestaurante = listarItensPorRestaurante;
        this.obterItemCardapioPorId = obterItemCardapioPorId;
        this.atualizarItemCardapio = atualizarItemCardapio;
        this.excluirItemCardapio = excluirItemCardapio;
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Listar itens por restaurante", description = "Lista os itens do cardápio de um restaurante")
    public ResponseEntity<List<ItemCardapioResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(listarItensPorRestaurante.execute(restauranteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID", description = "Retorna um item do cardápio pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemCardapioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obterItemCardapioPorId.execute(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar item", description = "Cria um novo item no cardápio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemCardapioResponseDTO> criar(@Valid @RequestBody CriarItemCardapioDTO dto) {
        ItemCardapioResponseDTO criado = cadastrarItemCardapio.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item", description = "Atualiza os dados do item do cardápio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item ou restaurante não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemCardapioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemCardapioUpdateDTO dto) {
        ItemCardapioResponseDTO atualizado = atualizarItemCardapio.execute(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item", description = "Remove um item do cardápio do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        excluirItemCardapio.execute(id);
        return ResponseEntity.noContent().build();
    }
}
