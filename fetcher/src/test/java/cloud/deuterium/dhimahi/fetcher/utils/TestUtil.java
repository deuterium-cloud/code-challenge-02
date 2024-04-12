package cloud.deuterium.dhimahi.fetcher.utils;

import cloud.deuterium.dhimahi.fetcher.dto.Data;
import cloud.deuterium.dhimahi.fetcher.dto.MetData;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by Milan Stojkovic 14-May-2023
 */
public class TestUtil {

    public static Data testData() {
        List<MetData> meteos = List.of(
                new MetData(45.6453, 14.8542, "KOCEVJE", 11d, 97d),
                new MetData(46.0933, 14.3756, "KATARINA", 9d, 95d)
        );
        return new Data(meteos);
    }

    public static Path xmlPath(){
        String path = "src/test/resources/xmls/meteo-data.xml";
        return Path.of(path);
    }
}
