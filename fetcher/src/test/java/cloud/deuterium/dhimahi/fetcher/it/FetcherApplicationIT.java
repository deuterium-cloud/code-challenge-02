package cloud.deuterium.dhimahi.fetcher.it;

import cloud.deuterium.dhimahi.fetcher.model.Meteo;
import cloud.deuterium.dhimahi.fetcher.repository.MeteoRepository;
import cloud.deuterium.dhimahi.fetcher.service.FetcherService;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.InputStream;
import java.util.Optional;

import static cloud.deuterium.dhimahi.fetcher.utils.TestUtil.xmlPath;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.file.Files.newInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FetcherApplicationIT extends BaseIT {

    @Autowired
    private MeteoRepository repository;

    @Autowired
    private FetcherService service;

    @BeforeEach
    void cleanDB() {
        repository.deleteAll();
    }

    @DisplayName("Should return 503 because Meteo server was failed")
    @Test
    void meteo_fail() throws Exception {
        stubFor(WireMock.get(urlMatching("/"))
                .willReturn(aResponse()
                        .withStatus(500)));

        mockMvc.perform(post("/v1/fetch"))
                .andExpect(status().is5xxServerError());
    }

    @DisplayName("Should fetch data, save to db and return")
    @Test
    void meteo_success() throws Exception {

        try (InputStream is = newInputStream(xmlPath())) {

            stubFor(WireMock.get(urlMatching("/"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/xml")
                            .withBody(is.readAllBytes())
                            .withStatus(200)));
        }

        mockMvc.perform(post("/v1/fetch")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.metData").isArray())
                .andExpect(jsonPath("$.metData[0].name").value("NOVA GORICA"));

        Optional<Meteo> celje = repository.findById("CELJE");

        assertTrue(celje.isPresent());
        assertEquals(celje.get().getTemperature(), 12);
    }

    @DisplayName("Should save List of Meteos to db")
    @Test
    void save_to_db() throws Exception {

        try (InputStream is = newInputStream(xmlPath())) {
            stubFor(WireMock.get(urlMatching("/"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/xml")
                            .withBody(is.readAllBytes())
                            .withStatus(200)));
        }

        service.process();

        Optional<Meteo> katarina = repository.findById("KATARINA");
        assertTrue(katarina.isPresent());
        assertEquals(katarina.get().getLocation().getX(), 46.0933);
        assertEquals(katarina.get().getLocation().getY(), 14.3756);
        assertEquals(katarina.get().getTemperature(), 9);
        assertEquals(katarina.get().getHumidity(), 96);
    }

}
