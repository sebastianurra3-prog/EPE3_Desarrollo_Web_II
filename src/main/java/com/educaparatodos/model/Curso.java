package com.educaparatodos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 80)
    private String tema; // p.ej: "Programación", "Idiomas", "Matemática"

    @Column(name = "nivel_dificultad", nullable = false, length = 20)
    private String nivelDificultad; // BASICO, INTERMEDIO, AVANZADO

    @Column(nullable = false)
    private Integer popularidad = 0; // cantidad de inscritos / visitas

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion = LocalDate.now();

    // Relación 1:N -> un curso tiene muchas lecciones
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Leccion> lecciones = new ArrayList<>();

    // Lado inverso de la relación N:M con Usuario
    @ManyToMany(mappedBy = "cursosInscritos", fetch = FetchType.LAZY)
    private Set<Usuario> usuariosInscritos = new HashSet<>();

    public Curso() {}

    public Curso(String titulo, String descripcion, String tema, String nivelDificultad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tema = tema;
        this.nivelDificultad = nivelDificultad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getNivelDificultad() { return nivelDificultad; }
    public void setNivelDificultad(String nivelDificultad) { this.nivelDificultad = nivelDificultad; }

    public Integer getPopularidad() { return popularidad; }
    public void setPopularidad(Integer popularidad) { this.popularidad = popularidad; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<Leccion> getLecciones() { return lecciones; }
    public void setLecciones(List<Leccion> lecciones) { this.lecciones = lecciones; }

    public Set<Usuario> getUsuariosInscritos() { return usuariosInscritos; }
    public void setUsuariosInscritos(Set<Usuario> usuariosInscritos) { this.usuariosInscritos = usuariosInscritos; }
}
