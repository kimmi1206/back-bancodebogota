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

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Elemento No Encontrado");

        Loggers.log(Level.SEVERE, "ERROR: NotFoundException --> Class, Method, Line Number: ({0})",
                Arrays.stream(ex.getStackTrace()).limit(5).toList());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Internal Server Error");

        Loggers.log(Level.SEVERE, "ERROR: NullPointerException --> Class, Method, Line Number: ({0})",
                Arrays.stream(ex.getStackTrace()).limit(5).toList());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(
            NumberFormatException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Valores no válidos");

        Loggers.log(Level.SEVERE, "ERROR: NumberFormatException --> Class, Method, Line Number: ({0})",
                Arrays.stream(ex.getStackTrace()).limit(5).toList());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Parametros Requeridos");

        Loggers.log(Level.SEVERE, "ERROR: IllegalArgumentException --> Class, Method, Line Number: ({0})",
                Arrays.stream(ex.getStackTrace()).limit(5).toList());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            HttpStatusCode status, @NonNull WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

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
        body.put("errors", errors);

        Loggers.log(Level.INFO, "ERROR: MethodArgumentNotValidException --> ({0})", body);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
