package br.com.fiap.tc.restaurant.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tc.restaurant.application.dto.AuthResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.LoginResquestDTO;
import br.com.fiap.tc.restaurant.application.usecase.auth.FazerLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Login", description = "Faz o login")
public class AuthController {
    
    private final FazerLogin fazerLogin;

    public AuthController(FazerLogin fazerLogin) {
        this.fazerLogin = fazerLogin;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente no sistema")
    // @ApiResponses(value = {
    //     @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
    //     @ApiResponse(responseCode = "400", description = "Dados inválidos",
    //         content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    //     @ApiResponse(responseCode = "409", description = "E-mail, login ou CPF já cadastrado",
    //         content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    // })
    public ResponseEntity<AuthResponseDTO> fazerLogin(@Valid @RequestBody LoginResquestDTO dto) {
        AuthResponseDTO login = fazerLogin.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(login);
    }
}
