package com.example.technology;

import com.example.technology.application.TechnologyService;
import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.TechnologyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = TechnologyController.class)
public class TechnologyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TechnologyService technologyService;

    private Technology sampleTechnology;

    @BeforeEach
    public void setUp() {
        sampleTechnology = new Technology(1L, "Tech", "Description");
    }

    @Test
    public void testCreateTechnology() {
        when(technologyService.createTechnology(any(Technology.class)))
                .thenReturn(Mono.just(sampleTechnology));

        webTestClient.post()
                .uri("/")
                .bodyValue(sampleTechnology)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Tech")
                .jsonPath("$.description").isEqualTo("Description");
    }

    @Test
    public void testGetTechnologyById() {
        when(technologyService.getTechnologyById(anyLong()))
                .thenReturn(Mono.just(sampleTechnology));

        webTestClient.get()
                .uri("/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Tech")
                .jsonPath("$.description").isEqualTo("Description");
    }

    @Test
    public void testListTechnologies() {
        when(technologyService.listTechnologies(anyInt(), anyInt(), any(String.class)))
                .thenReturn(Flux.just(sampleTechnology));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .queryParam("sort", "asc")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Tech")
                .jsonPath("$[0].description").isEqualTo("Description");
    }

    @Test
    public void testDeleteTechnology() {
        when(technologyService.deleteTechnology(anyLong()))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }
}
