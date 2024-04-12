package cloud.deuterium.dhimahi.query.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Milan Stojkovic 13-May-2023
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "meteos")
public class Meteo {

    @Id
    String name;
    GeoJsonPoint location;
    Double temperature;
    Double humidity;
}
