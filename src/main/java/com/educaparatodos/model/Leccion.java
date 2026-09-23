package com.educaparatodos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leccion")
public class Leccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 4000)
    private String contenido;

    @Column(nullable = false)
    private Integer orden = 1; // orden de la lección dentro del curso

    // Relación N:1 -> muchas lecciones pertenecen a un curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public Leccion() {}

    public Leccion(String titulo, String contenido, Integer orden, Curso curso) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.orden = orden;
        this.curso = curso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
