package cloud.deuterium.dhimahi.fetcher.controller;

import cloud.deuterium.dhimahi.fetcher.dto.Data;
import cloud.deuterium.dhimahi.fetcher.service.FetcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * Created by Milan Stojkovic 13-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/fetch")
public class FetcherController {

    private final FetcherService service;

    @PostMapping
    public ResponseEntity<Data> fetch() {

        log.info("Controller: Initiate fetch data at: {}", Instant.now());
        Data data = service.process();

        return  ResponseEntity.ok(data);
    }

}
