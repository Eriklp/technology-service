package com.example.technology.api;

import com.example.technology.application.TechnologyService;
import com.example.technology.domain.Technology;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }
    @Operation(summary = "Get all technologies", description = "Retrieves a paginated list of technologies")
    @GetMapping
    public ResponseEntity<List<Technology>> getAllTechnologies(
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Technology> technologies = technologyService.getAllTechnologies(sortDirection, page, size);
        return new ResponseEntity<>(technologies, HttpStatus.OK);
    }
    @Operation(summary = "Get technology by id", description = "Retrieves a technology by id")
    @GetMapping("/{id}")
    public ResponseEntity<Technology> getTechnologyById(@PathVariable Long id) {
        Technology technology = technologyService.getTechnologyById(id);
        return technology != null ? new ResponseEntity<>(technology, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Post create technology", description = "Create new technology")
    @PostMapping
    public ResponseEntity<Technology> createTechnology(@Valid @RequestBody Technology technology) {
        try {
            Technology createdTechnology = technologyService.saveTechnology(technology);
            return new ResponseEntity<>(createdTechnology, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Delete technology by Id", description = "Delete technology by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
