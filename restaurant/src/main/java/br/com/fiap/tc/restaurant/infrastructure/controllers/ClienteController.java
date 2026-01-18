package br.com.fiap.tc.restaurant.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.application.dto.ClienteUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.cliente.AtualizarCliente;
import br.com.fiap.tc.restaurant.application.usecase.cliente.CadastrarCliente;
import br.com.fiap.tc.restaurant.application.usecase.cliente.ExcluirCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "API de gerenciamento de clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;
    private final ExcluirCliente excluirCliente;
    private final AtualizarCliente atualizarCliente;

    public ClienteController(CadastrarCliente cadastrarCliente, ExcluirCliente excluirCliente, AtualizarCliente atualizarCliente) {
        this.cadastrarCliente = cadastrarCliente;
        this.excluirCliente = excluirCliente;
        this.atualizarCliente = atualizarCliente;
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
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@Valid @RequestBody CriarClienteDTO dto) {
        ClienteResponseDTO cliente = cadastrarCliente.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        excluirCliente.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do cliente",
        description = "Atualiza nome, email, telefone, data de nascimento e endereço (não inclui senha)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO cliente = atualizarCliente.execute(id, dto);
        return ResponseEntity.ok(cliente);
    }

}
