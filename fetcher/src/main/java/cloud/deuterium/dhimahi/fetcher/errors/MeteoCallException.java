package cloud.deuterium.dhimahi.fetcher.errors;

/**
 * Created by Milan Stojkovic 14-May-2023
 */
public class MeteoCallException extends RuntimeException {
    public MeteoCallException(String message) {
        super(message);
    }
}
