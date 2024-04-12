package cloud.deuterium.dhimahi.query.controller;

import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import cloud.deuterium.dhimahi.query.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by Milan Stojkovic 15-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/current")
public class QueryController {

    private final QueryService service;

    @GetMapping
    public Flux<MeteoResponse> getCurrent(@RequestParam(required = false) Double lat,
                                          @RequestParam(required = false) Double lon) {

        log.info("Get current Meteo, with params lat={} and lon={}", lat, lon);

        return service.getCurrent(lat, lon);
    }
}
