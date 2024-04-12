package cloud.deuterium.dhimahi.query.service;

import cloud.deuterium.dhimahi.query.model.Meteo;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
public class MeteoMapper {

    public static MeteoResponse map(Meteo meteo) {
        return getMeteoResponse(meteo, null);
    }

    public static MeteoResponse map(GeoResult<Meteo> geoResult) {
        Meteo meteo = geoResult.getContent();
        Distance distance = geoResult.getDistance();
        double distanceInMeters = distance.getValue() * 1000;
        return getMeteoResponse(meteo, distanceInMeters);
    }

    private static MeteoResponse getMeteoResponse(Meteo meteo, Double distance) {
        return MeteoResponse.builder()
                .lat(meteo.getLocation().getX())
                .lon(meteo.getLocation().getY())
                .name(meteo.getName())
                .temperature(meteo.getTemperature())
                .humidity(meteo.getHumidity())
                .distance(distance)
                .build();
    }
}
