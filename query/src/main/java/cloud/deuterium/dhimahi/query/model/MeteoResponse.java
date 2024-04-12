package cloud.deuterium.dhimahi.query.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 15-May-2023
 */

@Getter
@Setter
@Builder
public class MeteoResponse {
    double lat;
    double lon;
    String name;
    Double distance;
    Double temperature;
    Double humidity;
}
