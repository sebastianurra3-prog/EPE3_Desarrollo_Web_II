package com.educaparatodos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    @Column(length = 20)
    private String rol = "ESTUDIANTE"; // ESTUDIANTE o ADMIN

    // Relación N:M -> un usuario se inscribe en muchos cursos y un curso tiene muchos usuarios
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_curso",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursosInscritos = new HashSet<>();

    public Usuario() {}

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Set<Curso> getCursosInscritos() { return cursosInscritos; }
    public void setCursosInscritos(Set<Curso> cursosInscritos) { this.cursosInscritos = cursosInscritos; }
}
