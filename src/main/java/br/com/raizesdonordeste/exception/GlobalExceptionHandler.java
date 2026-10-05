package br.com.raizesdonordeste.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> tratarResponseStatus(
            ResponseStatusException exception) {

        HttpStatus status = HttpStatus.valueOf(
                exception.getStatusCode().value());

        return criarResposta(
                status,
                exception.getReason());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> tratarRuntimeException(
            RuntimeException exception) {

        String mensagem = exception.getMessage();

        HttpStatus status = switch (mensagem) {

            case "Usuário não encontrado",
                 "Unidade não encontrada",
                 "Produto não encontrado",
                 "Pedido não encontrado" ->
                    HttpStatus.NOT_FOUND;

            case "Estoque insuficiente",
                 "Produto indisponível",
                 "Este pedido já possui um pagamento" ->
                    HttpStatus.CONFLICT;

            default -> HttpStatus.BAD_REQUEST;
        };

        return criarResposta(status, mensagem);
    }

    private ResponseEntity<Map<String, Object>> criarResposta(
            HttpStatus status,
            String mensagem) {

        Map<String, Object> erro = new LinkedHashMap<>();

        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", status.value());
        erro.put("erro", status.getReasonPhrase());
        erro.put("mensagem", mensagem);

        return ResponseEntity
                .status(status)
                .body(erro);
    }
}