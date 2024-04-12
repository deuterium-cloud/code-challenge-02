package cloud.deuterium.dhimahi.fetcher.unit;

import cloud.deuterium.dhimahi.fetcher.dto.Data;
import cloud.deuterium.dhimahi.fetcher.errors.MeteoCallException;
import cloud.deuterium.dhimahi.fetcher.model.Meteo;
import cloud.deuterium.dhimahi.fetcher.repository.MeteoRepository;
import cloud.deuterium.dhimahi.fetcher.service.FetcherService;
import cloud.deuterium.dhimahi.fetcher.service.MeteoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static cloud.deuterium.dhimahi.fetcher.utils.TestUtil.testData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@ExtendWith(SpringExtension.class)
public class FetcherServiceTest {

    @Mock
    private MeteoRepository repository;
    @Mock
    private MeteoService meteoService;
    @InjectMocks
    private FetcherService service;

    @Captor
    ArgumentCaptor<List<Meteo>> captor;

    @DisplayName("Should save List of Meteos to db")
    @Test
    void save_to_db() {

        Data data = testData();
        when(meteoService.fetch()).thenReturn(data);

        service.process();

        verify(meteoService, times(1)).fetch();
        verify(repository, times(1)).saveAll(captor.capture());
        assertEquals(2, captor.getValue().size());
    }

    @DisplayName("Should throw an MeteoCallException when MeteoService return null")
    @Test
    void throw_exception() {

        when(meteoService.fetch()).thenReturn(null);

        assertThrows(MeteoCallException.class,
                () -> service.process());
    }
}
