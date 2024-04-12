package cloud.deuterium.dhimahi.fetcher.service;

import cloud.deuterium.dhimahi.fetcher.dto.MetData;
import cloud.deuterium.dhimahi.fetcher.model.Meteo;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 13-May-2023
 */
public class MeteoMapper {

    public static Meteo map(MetData data) {

        if (isNull(data)) return null;

        return Meteo.builder()
                .name(data.getName())
                .location(new GeoJsonPoint(data.getLat(), data.getLon()))
                .temperature(data.getTemperature())
                .humidity(data.getHumidity())
                .build();
    }
}
