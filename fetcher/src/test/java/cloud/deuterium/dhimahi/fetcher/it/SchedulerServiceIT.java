package cloud.deuterium.dhimahi.fetcher.it;

import cloud.deuterium.dhimahi.fetcher.repository.MeteoRepository;
import cloud.deuterium.dhimahi.fetcher.schedule.ScheduleService;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static cloud.deuterium.dhimahi.fetcher.utils.TestUtil.xmlPath;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


/**
 * Created by Milan Stojkovic 14-May-2023
 */
public class SchedulerServiceIT extends BaseIT {

    @Autowired
    private MeteoRepository repository;

    @BeforeEach
    void cleanDB() {
        repository.deleteAll();
    }

    @SpyBean
    private ScheduleService scheduleService;

    @DisplayName("Should start scheduler")
    @Test
    void scheduler() throws Exception{

        try (InputStream is = Files.newInputStream(xmlPath())) {

            stubFor(WireMock.get(urlMatching("/"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/xml")
                            .withBody(is.readAllBytes())
                            .withStatus(200)));
        }

        await().atMost(3000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(scheduleService, atLeast(1)).fetch());
    }

}
