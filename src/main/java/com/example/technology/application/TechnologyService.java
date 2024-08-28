package com.example.technology.application;

import com.example.technology.domain.Technology;
import com.example.technology.infrastructure.TechnologyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@Service
@Validated
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public List<Technology> getAllTechnologies(String sortDirection, int page, int size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "name"));
        return technologyRepository.findAll(pageable).getContent();
    }


    public Technology getTechnologyById(Long id) {
        return technologyRepository.findById(id).orElse(null);
    }

    public Technology saveTechnology(@Valid Technology technology) {
        if (technologyRepository.existsByName(technology.getName())) {
            throw new IllegalArgumentException("El nombre de la tecnología ya existe");
        }
        return technologyRepository.save(technology);
    }

    public void deleteTechnology(Long id) {
        technologyRepository.deleteById(id);
    }
}
