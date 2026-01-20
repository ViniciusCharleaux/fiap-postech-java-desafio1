package br.com.fiap.tc.restaurant.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class HandleValidationExceptionTest {

    static class DummyDto { public String nome; }
    static class DummyController { public void create(DummyDto dto) {} }

    @Test
    @DisplayName("handleValidationException deve retornar ProblemDetail 400 com mapa de erros")
    void handleValidation_returnsProblemDetail() throws NoSuchMethodException {
        // Arrange: cria BindingResult com um FieldError
        DummyDto target = new DummyDto();
        BindingResult br = new BeanPropertyBindingResult(target, "dummyDto");
        br.addError(new FieldError("dummyDto", "nome", "Nome é obrigatório"));

        MethodParameter param = new MethodParameter(
                DummyController.class.getDeclaredMethod("create", DummyDto.class), 0);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(param, br);

        HandleValidationException handler = new HandleValidationException();

        // Act
        ProblemDetail pd = handler.handleValidationException(ex);

        // Assert
        assertNotNull(pd);
        assertEquals(HttpStatus.BAD_REQUEST.value(), pd.getStatus());
        assertEquals("Erro de validação", pd.getTitle());
        assertTrue(pd.getProperties().containsKey("errors"));
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) pd.getProperties().get("errors");
        assertEquals("Nome é obrigatório", errors.get("nome"));
        assertTrue(pd.getProperties().containsKey("timestamp"));
    }
}
