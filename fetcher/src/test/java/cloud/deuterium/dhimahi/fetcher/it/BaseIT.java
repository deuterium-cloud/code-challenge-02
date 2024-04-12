package cloud.deuterium.dhimahi.fetcher.it;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    final static MongoDBContainer MONGO = new MongoDBContainer("mongo:4.4.4")
            .withExposedPorts(27017);

    final static WireMockServer WM = new WireMockServer(
            new WireMockConfiguration().port(9999)
    );

    @DynamicPropertySource
    private static void registerProperties(DynamicPropertyRegistry registry) {

        MONGO.start();
        registry.add("spring.data.mongodb.uri", MONGO::getReplicaSetUrl);

        WM.start();
        WireMock.configureFor("localhost", 9999);
    }

}
