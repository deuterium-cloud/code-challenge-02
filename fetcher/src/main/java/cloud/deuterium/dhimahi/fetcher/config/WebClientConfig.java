package cloud.deuterium.dhimahi.fetcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by Milan Stojkovic 13-May-2023
 */

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${app.meteo.url}") String meteoUrl) {
        return WebClient.builder()
                .baseUrl(meteoUrl)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer ->
                                        configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder())
                                )
                                .build()
                )
                .build();
    }
}
