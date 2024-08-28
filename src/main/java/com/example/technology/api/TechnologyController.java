package com.example.technology.api;

import com.example.technology.application.TechnologyService;
import com.example.technology.domain.Technology;
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

    @GetMapping
    public ResponseEntity<List<Technology>> getAllTechnologies(
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Technology> technologies = technologyService.getAllTechnologies(sortDirection, page, size);
        return new ResponseEntity<>(technologies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technology> getTechnologyById(@PathVariable Long id) {
        Technology technology = technologyService.getTechnologyById(id);
        return technology != null ? new ResponseEntity<>(technology, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Technology> createTechnology(@Valid @RequestBody Technology technology) {
        try {
            Technology createdTechnology = technologyService.saveTechnology(technology);
            return new ResponseEntity<>(createdTechnology, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
