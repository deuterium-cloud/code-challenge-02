package cloud.deuterium.dhimahi.query.repository;

import cloud.deuterium.dhimahi.query.model.Meteo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
public interface MeteoRepository extends ReactiveMongoRepository<Meteo, String> {
}
