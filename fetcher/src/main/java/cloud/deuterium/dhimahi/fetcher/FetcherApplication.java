package cloud.deuterium.dhimahi.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetcherApplication.class, args);
	}

}
