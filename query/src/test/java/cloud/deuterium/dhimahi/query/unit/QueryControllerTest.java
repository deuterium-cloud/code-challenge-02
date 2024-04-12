package cloud.deuterium.dhimahi.query.unit;

import cloud.deuterium.dhimahi.query.controller.QueryController;
import cloud.deuterium.dhimahi.query.model.MeteoResponse;
import cloud.deuterium.dhimahi.query.service.QueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static cloud.deuterium.dhimahi.query.utils.TestUtil.getOneResponse;
import static cloud.deuterium.dhimahi.query.utils.TestUtil.getResponses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = QueryController.class)
public class QueryControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    private QueryService service;

    @DisplayName("Should return data from the nearest station")
    @Test
    void get_current() {

        when(service.getCurrent(anyDouble(), anyDouble())).thenReturn(getOneResponse());

        webClient.get()
                .uri("/v1/current?lat=45.64&lon=15")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MeteoResponse.class).value(list -> {
                    assertThat(list.size()).isEqualTo(1);
                    assertThat(list.get(0).getName()).isEqualTo("KOCEVJE");
                });

        verify(service, times(1)).getCurrent(anyDouble(), anyDouble());
    }

    @DisplayName("Should return all data")
    @Test
    void get_all() {

        when(service.getCurrent(null, null)).thenReturn(getResponses());

        webClient.get()
                .uri("/v1/current")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MeteoResponse.class).value(list -> {
                    assertThat(list.size()).isEqualTo(3);
                    assertThat(list.get(0).getName()).isEqualTo("KOCEVJE");
                    assertThat(list.get(1).getName()).isEqualTo("KATARINA");
                    assertThat(list.get(2).getName()).isEqualTo("CRNOMELJ");
                });

        verify(service, times(1)).getCurrent(null, null);
    }

}
