package cloud.deuterium.dhimahi.query.it;

import cloud.deuterium.dhimahi.query.model.Meteo;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import cloud.deuterium.dhimahi.query.repository.MeteoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.test.web.reactive.server.WebTestClient;

import static cloud.deuterium.dhimahi.query.utils.TestUtil.getMeteos;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
public class QueryApplicationIT extends BaseIT {

    @Autowired
    WebTestClient webClient;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    MeteoRepository repository;

    @BeforeEach
    void seedDB() {
        mongoTemplate.indexOps(Meteo.class)
                .ensureIndex(new GeospatialIndex("location")
                        .typed(GeoSpatialIndexType.GEO_2DSPHERE))
                .block();
        repository.deleteAll()
                .block();
        repository.saveAll(getMeteos()).collectList()
                .block();
    }

    @DisplayName("Should return data from the nearest station")
    @Test
    void get_current() {

        webClient.get()
                .uri("/v1/current?lat=45&lon=14.85")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MeteoResponse.class).value(list -> {
                    assertThat(list.size()).isEqualTo(1);
                    assertThat(list.get(0).getName()).isEqualTo("KOCEVJE");
                });
    }

    @DisplayName("Should return all data")
    @Test
    void get_all() {

        webClient.get()
                .uri("/v1/current")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MeteoResponse.class).value(list -> {
                    assertThat(list.size()).isEqualTo(3);
                });
    }
}
