package cloud.deuterium.dhimahi.fetcher.errors;

import java.time.Instant;

/**
 * Created by Milan Stojkovic 14-May-2023
 */
public record ErrorResponse(String message, int status, Instant timestamp) {
}
