package cloud.deuterium.dhimahi.fetcher.unit;

import cloud.deuterium.dhimahi.fetcher.controller.FetcherController;
import cloud.deuterium.dhimahi.fetcher.service.FetcherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static cloud.deuterium.dhimahi.fetcher.utils.TestUtil.testData;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Milan Stojkovic 14-May-2023
 */

@ExtendWith(SpringExtension.class)
public class FetchControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FetcherService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new FetcherController(service))
                .build();
    }

    @Test
    @DisplayName("Should Initiate fetch data with HTTP request")
    void initiate_fetch() throws Exception {

        when(service.process()).thenReturn(testData());

        this.mockMvc.perform(post("/v1/fetch")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.metData").isArray())
                .andExpect(jsonPath("$.metData[0].name").value("KOCEVJE"))
                .andExpect(jsonPath("$.metData[1].name").value("KATARINA"));

        verify(service, times(1)).process();
    }
}
