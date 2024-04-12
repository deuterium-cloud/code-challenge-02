package cloud.deuterium.dhimahi.query.service;

import cloud.deuterium.dhimahi.query.model.Meteo;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import cloud.deuterium.dhimahi.query.repository.MeteoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 15-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class QueryService {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MeteoRepository repository;

    public Flux<MeteoResponse> getCurrent(Double lat, Double lon) {

        if (isNull(lat) || isNull(lon)) {
            log.debug("Query DB for all Meteos at {}", Instant.now());
            return findAll();
        }
        log.debug("Query DB for nearest Meteo, with lat={} and lon={}", lat, lon);
        return findOne(lat, lon);
    }

    private Flux<MeteoResponse> findAll() {
        return repository.findAll()
                .map(MeteoMapper::map);
    }

    private Flux<MeteoResponse> findOne(Double lat, Double lon) {
        NearQuery query = getQuery(lat, lon);
        return mongoTemplate.geoNear(query, Meteo.class)
                .map(MeteoMapper::map);
    }

    private static NearQuery getQuery(double lat, double lon) {
        NearQuery near = NearQuery.near(new GeoJsonPoint(lat, lon));
        near.maxDistance(20000, Metrics.KILOMETERS);
        near.minDistance(0, Metrics.KILOMETERS);
        near.skip(0);
        near.limit(1);
        return near;
    }
}
