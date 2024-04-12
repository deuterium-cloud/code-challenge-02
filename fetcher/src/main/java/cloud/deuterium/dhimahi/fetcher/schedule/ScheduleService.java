package cloud.deuterium.dhimahi.fetcher.schedule;

import cloud.deuterium.dhimahi.fetcher.service.FetcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleService {

    private final FetcherService service;

    @Scheduled(fixedRateString = "${app.scheduler.rate-ms}")
    public void fetch(){

        log.info("Scheduler: Initiate fetch data from at: {}", Instant.now());
        service.process();
    }
}
