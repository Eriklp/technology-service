package com.example.technology.domain.repository;

import com.example.technology.domain.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    boolean existsByName(String name);

    @Query("SELECT t FROM Technology t ORDER BY CASE WHEN :sort = 'asc' THEN t.name ELSE NULL END ASC, CASE WHEN :sort = 'desc' THEN t.name ELSE NULL END DESC")
    List<Technology> findAllWithSorting(@Param("sort") String sort);

    /**
     * Encuentra todas las tecnologías cuyos nombres están incluidos en la lista proporcionada.
     * @param names Lista de nombres para buscar.
     * @return Lista de tecnologías que coinciden con los nombres dados.
     */
    List<Technology> findByNameIn(List<String> names);
}

