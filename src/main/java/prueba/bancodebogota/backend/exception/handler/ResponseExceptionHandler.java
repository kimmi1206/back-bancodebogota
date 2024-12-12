package prueba.bancodebogota.backend.exception.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prueba.bancodebogota.backend.exception.type.NotFoundException;

import java.time.*;
import java.util.*;
import java.util.logging.*;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger Loggers = Logger.getLogger(ResponseExceptionHandler.class.getName());

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException ex, WebRequest request) {

        return buildResponseEntity(HttpStatus.NOT_FOUND, "Not found", "Elemento No Encontrado", null, null, null);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        Loggers.log(Level.SEVERE, "ERROR: NullPointerException --> Class, Method, Line Number: ({0})",
                Arrays.stream(ex.getStackTrace()).limit(5).toList());

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Null pointer", "Internal Server Error", LocalDateTime.now(), null, null);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(
            NumberFormatException ex, WebRequest request) {

        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Number format", "Valores no válidos", null, null, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Illegal argument", "Parametros Requeridos", null, null, null);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            HttpStatusCode status, @NonNull WebRequest request) {

        List<String> errors = new ArrayList<>();
        try {
            errors = ex.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
        } catch (Exception exc) {
            Loggers.log(Level.SEVERE, exc.getMessage());
        }

        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Method argument not valid", null, null, status, errors);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String error, String message, LocalDateTime timestamp, HttpStatusCode statusBody, List<String> errors) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("error", error);
        if (message != null) {
            body.put("message", message);
        }
        if (timestamp != null) {
            body.put("timestamp", timestamp);
        }
        if (statusBody != null) {
            body.put("status", statusBody.value());
        }
        if (errors != null) {
            body.put("errors", errors);
        }

        response.put("data", body);
        response.put("status", status.value());
        response.put("statusText", status.getReasonPhrase());

        return new ResponseEntity<>(response, status);
    }
}
