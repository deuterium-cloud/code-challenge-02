package cloud.deuterium.dhimahi.fetcher.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import cloud.deuterium.dhimahi.fetcher.model.Meteo;

/**
 * Created by Milan Stojkovic 14-May-2023
 */
public interface MeteoRepository extends MongoRepository<Meteo, String> {
}
