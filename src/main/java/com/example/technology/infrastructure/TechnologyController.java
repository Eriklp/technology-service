package com.example.technology.infrastructure;

import com.example.technology.application.TechnologyService;
import com.example.technology.domain.model.Technology;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @Operation(summary = "Post create technology", description = "Create new technology")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Technology> createTechnology(@Valid @RequestBody Technology technology) {
        return technologyService.createTechnology(technology);
    }

    @Operation(summary = "Get technology by id", description = "Retrieves a technology by id")
    @GetMapping("/{id}")
    public Mono<Technology> getTechnologyById(@PathVariable Long id) {
        return technologyService.getTechnologyById(id);
    }

    @Operation(summary = "Get all technologies", description = "Retrieves a paginated list of technologies")
    @GetMapping
    public Flux<Technology> listTechnologies(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "asc") String sort) {
        return technologyService.listTechnologies(page, size, sort);
    }

    @Operation(summary = "Delete technology by Id", description = "Delete technology by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTechnology(@PathVariable Long id) {
        return technologyService.deleteTechnology(id);
    }
}

