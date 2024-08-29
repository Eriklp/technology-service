package com.example.technology.domain.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

@Entity
@Table(name = "technologies")
public class Technology {

    @Id
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El tamaño máximo del nombre es 50 caracteres")
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 90, message = "El tamaño máximo de la descripción es 90 caracteres")
    private String description;

    public Technology() {
    }
    public Technology(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
