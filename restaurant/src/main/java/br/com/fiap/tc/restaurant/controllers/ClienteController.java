package br.com.fiap.tc.restaurant.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.services.ClienteService;
import br.com.fiap.tc.restaurant.services.UsuarioService;
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

    private final UsuarioService userService;
    private final ClienteService clienteService;

    public ClienteController(UsuarioService userService, ClienteService clienteService) {
        this.userService = userService;
        this.clienteService = clienteService;
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
        ClienteResponseDTO cliente = clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

}
