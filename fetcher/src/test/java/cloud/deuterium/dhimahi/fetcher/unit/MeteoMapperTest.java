package cloud.deuterium.dhimahi.fetcher.unit;

import cloud.deuterium.dhimahi.fetcher.dto.MetData;
import cloud.deuterium.dhimahi.fetcher.model.Meteo;
import cloud.deuterium.dhimahi.fetcher.service.MeteoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by Milan Stojkovic 14-May-2023
 */
public class MeteoMapperTest {

    @DisplayName("Should return new mapped Meteo object")
    @Test
    void map() {
        MetData kocevje = new MetData(45.6453, 14.8542, "KOCEVJE", 11d, 97d);
        Meteo mapped = MeteoMapper.map(kocevje);
        assertEquals(45.6453, mapped.getLocation().getX());
        assertEquals(14.8542, mapped.getLocation().getY());
        assertEquals("KOCEVJE", mapped.getName());
        assertEquals(11d, mapped.getTemperature());
        assertEquals(97d, mapped.getHumidity());
    }

    @DisplayName("Should return null when data is null")
    @Test
    void map_null() {
        Meteo mapped = MeteoMapper.map(null);
        assertNull(mapped);
    }
}
