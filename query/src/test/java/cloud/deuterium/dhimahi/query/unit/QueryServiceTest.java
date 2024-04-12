package cloud.deuterium.dhimahi.query.unit;

import cloud.deuterium.dhimahi.query.model.Meteo;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import cloud.deuterium.dhimahi.query.repository.MeteoRepository;
import cloud.deuterium.dhimahi.query.service.QueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static cloud.deuterium.dhimahi.query.utils.TestUtil.getGeoResult;
import static cloud.deuterium.dhimahi.query.utils.TestUtil.getMeteos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Milan Stojkovic 15-May-2023
 */

@ExtendWith(SpringExtension.class)
public class QueryServiceTest {

    @InjectMocks
    private QueryService service;

    @Mock
    private MeteoRepository repository;
    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    @DisplayName("Should fetch one Meteo from DB using repository")
    @Test
    public void get_one() {

        when(mongoTemplate.geoNear(any(NearQuery.class), eq(Meteo.class))).thenReturn(getGeoResult());

        Flux<MeteoResponse> flux = service.getCurrent(30d, 15d);

        StepVerifier
                .create(flux)
                .consumeNextWith(meteo -> {
                    assertEquals(meteo.getName(), "KOCEVJE");
                })
                .verifyComplete();

        verify(repository, times(0)).findAll();
        verify(mongoTemplate, times(1)).geoNear(any(NearQuery.class), eq(Meteo.class));
    }

    @DisplayName("Should fetch all Meteos from DB using mongoTemplate")
    @Test
    public void get_all() {

        when(repository.findAll()).thenReturn(Flux.fromIterable(getMeteos()));

        Flux<MeteoResponse> flux = service.getCurrent(null, null);

        StepVerifier
                .create(flux)
                .consumeNextWith(meteo -> {
                    assertEquals(meteo.getName(), "KOCEVJE");
                })
                .consumeNextWith(meteo -> {
                    assertEquals(meteo.getName(), "KATARINA");
                })
                .consumeNextWith(meteo -> {
                    assertEquals(meteo.getName(), "CRNOMELJ");
                })
                .verifyComplete();

        verify(repository, times(1)).findAll();
        verify(mongoTemplate, times(0)).geoNear(any(NearQuery.class), eq(Meteo.class));
    }
}
