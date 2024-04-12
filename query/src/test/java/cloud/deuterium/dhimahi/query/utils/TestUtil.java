package cloud.deuterium.dhimahi.query.utils;

import cloud.deuterium.dhimahi.query.model.Meteo;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
public class TestUtil {

    public static Flux<MeteoResponse> getOneResponse() {
        return Flux.just("KOCEVJE")
                .map(TestUtil::createMeteoResponse);
    }

    public static Flux<MeteoResponse> getResponses() {
        return Flux.just("KOCEVJE", "KATARINA", "CRNOMELJ")
                .map(TestUtil::createMeteoResponse);
    }

    public static List<Meteo> getMeteos() {
        return List.of(Meteo.builder()
                        .name("KOCEVJE")
                        .location(new GeoJsonPoint(45.6453, 14.8542))
                        .temperature(11d)
                        .humidity(97d)
                        .build(),
                Meteo.builder()
                        .name("KATARINA")
                        .location(new GeoJsonPoint(46.0933, 14.3756))
                        .temperature(9d)
                        .humidity(85d)
                        .build(),
                Meteo.builder()
                        .name("CRNOMELJ")
                        .location(new GeoJsonPoint(45.5603, 5.1508))
                        .temperature(8d)
                        .humidity(90d)
                        .build()
        );
    }

    public static Flux<GeoResult<Meteo>> getGeoResult() {
        Distance distance = new Distance(10, Metrics.KILOMETERS);
        Meteo kocevje = createMeteo("KOCEVJE");
        GeoResult<Meteo> result = new GeoResult<>(kocevje, distance);
        return Flux.just(result);
    }

    private static Meteo createMeteo(String name) {
        return Meteo.builder()
                .name(name)
                .location(new GeoJsonPoint(45.6453, 14.8542))
                .temperature(11d)
                .humidity(97d)
                .build();
    }

    public static MeteoResponse createMeteoResponse(String name) {
        return MeteoResponse.builder()
                .name(name)
                .lat(45.6453)
                .lon(14.8542)
                .temperature(11d)
                .humidity(97d)
                .build();
    }
}
