package cloud.deuterium.dhimahi.query.config;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * Created by Milan Stojkovic 15-May-2023
 */
@Configuration
public class ReactiveMongoTemplateConfig {

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, "meteodb");
    }
}
