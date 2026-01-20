package br.com.fiap.tc.restaurant.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

class ControllerAdviceTest {

    @Test
    @DisplayName("handleAllExceptions deve retornar ResponseEntity com corpo não nulo")
    void handleAllExceptions_returnsBody() {
        ControllerAdvice advice = new ControllerAdvice();
        Exception ex = new RuntimeException("erro");
        WebRequest req = Mockito.mock(WebRequest.class);

        ResponseEntity<?> resp = advice.handleAllExceptions(ex, req);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
    }
}
