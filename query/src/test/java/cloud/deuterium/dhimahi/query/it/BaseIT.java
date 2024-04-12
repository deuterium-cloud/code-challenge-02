package cloud.deuterium.dhimahi.query.it;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest
abstract class BaseIT {

    final static MongoDBContainer MONGO = new MongoDBContainer("mongo:4.4.4")
            .withExposedPorts(27017);

    @DynamicPropertySource
    private static void registerProperties(DynamicPropertyRegistry registry) {

        MONGO.start();
        registry.add("spring.data.mongodb.uri", MONGO::getReplicaSetUrl);
    }



}
