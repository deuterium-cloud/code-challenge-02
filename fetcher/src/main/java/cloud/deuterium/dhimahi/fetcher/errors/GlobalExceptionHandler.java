package cloud.deuterium.dhimahi.fetcher.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MeteoCallException.class)
    public ResponseEntity<ErrorResponse> meteoCallException(MeteoCallException e) {
        log.error("MeteoCallException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse(e.getMessage(), 503, Instant.now()));
    }
}
