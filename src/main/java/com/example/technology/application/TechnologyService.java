package com.example.technology.application;


import com.example.technology.domain.model.Technology;
import com.example.technology.domain.repository.TechnologyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Mono<Technology> createTechnology(Technology technology) {
        return Mono.fromCallable(() -> {
            if (technologyRepository.existsByName(technology.getName())) {
                throw new IllegalArgumentException("Technology name already exists.");
            }
            return technologyRepository.save(technology);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Technology> getTechnologyById(Long id) {
        return Mono.fromCallable(() -> technologyRepository.findById(id).orElse(null))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<Technology> listTechnologies(int page, int size, String sort) {
        // Manual pagination
        return Mono.fromCallable(() -> {
                    List<Technology> technologies = technologyRepository.findAllWithSorting(sort);
                    int start = Math.min(page * size, technologies.size());
                    int end = Math.min(start + size, technologies.size());
                    return technologies.subList(start, end);
                }).flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> deleteTechnology(Long id) {
        return Mono.fromRunnable(() -> technologyRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}


