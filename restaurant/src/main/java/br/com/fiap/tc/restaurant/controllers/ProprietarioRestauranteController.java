package br.com.fiap.tc.restaurant.controllers;

import br.com.fiap.tc.restaurant.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.services.ClienteService;
import br.com.fiap.tc.restaurant.services.ProprietarioRestauranteService;
import br.com.fiap.tc.restaurant.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/proprietarios")
@Tag(name = "Proprietarios", description = "API de gerenciamento de proprietarios de restaurantes")
public class ProprietarioRestauranteController {
    private final UsuarioService userService;
    private final ProprietarioRestauranteService proprietarioRestauranteService;

    public ProprietarioRestauranteController(UsuarioService userService, ProprietarioRestauranteService proprietarioRestauranteService) {
        this.userService = userService;
        this.proprietarioRestauranteService = proprietarioRestauranteService;
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
        ProprietarioResponseDTO cliente = proprietarioRestauranteService.cadastrarProprietario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
}
