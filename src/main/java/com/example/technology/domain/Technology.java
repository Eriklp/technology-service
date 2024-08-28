package com.example.technology.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "technologies", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    @Column(nullable = false, length = 90)
    private String description;

    protected Technology() {
    }
    // Constructor con parámetros
    public Technology(String name, String description) {
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
