package cloud.deuterium.dhimahi.fetcher.service;

import cloud.deuterium.dhimahi.fetcher.dto.Data;
import cloud.deuterium.dhimahi.fetcher.errors.MeteoCallException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Created by Milan Stojkovic 13-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class MeteoService {

    private final WebClient client;

    public Data fetch() {

        log.info("Fetching data from External Arso service at {}", Instant.now());

        return client.get()
                .retrieve()
                .onStatus(status -> status.value() != 200,
                        res -> Mono.error(new MeteoCallException("Something goes wrong! Code: " + res.statusCode().value())))
                .bodyToMono(Data.class)
                .block();
    }
}
