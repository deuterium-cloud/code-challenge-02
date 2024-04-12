package cloud.deuterium.dhimahi.fetcher.service;

import cloud.deuterium.dhimahi.fetcher.dto.Data;
import cloud.deuterium.dhimahi.fetcher.errors.MeteoCallException;
import cloud.deuterium.dhimahi.fetcher.model.Meteo;
import cloud.deuterium.dhimahi.fetcher.repository.MeteoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class FetcherService {

    private final MeteoRepository repository;
    private final MeteoService meteoService;

    public Data process() {

        Data data = meteoService.fetch();

        if (isNull(data)) throw new MeteoCallException("The response has no data");

        List<Meteo> meteos = data.getMetData()
                .stream()
                .map(MeteoMapper::map)
                .toList();

        log.info("Saving {} Meteos to database", meteos.size());

        repository.saveAll(meteos);

        return data;
    }

}
