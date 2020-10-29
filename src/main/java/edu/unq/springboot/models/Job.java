package edu.unq.springboot.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User owner;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicioTrabajo;
    private LocalDate fechaFinTrabajo;

    public Job() {}

    public Job(User usuario, String titulo, String descripcion, LocalDate fechaInicioTrabajo, LocalDate fechaFinTrabajo) {
        this.owner = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicioTrabajo = fechaInicioTrabajo;
        this.fechaFinTrabajo = fechaFinTrabajo;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicioTrabajo() {
        return fechaInicioTrabajo;
    }

    public void setFechaInicioTrabajo(LocalDate fechaInicioTrabajo) {
        this.fechaInicioTrabajo = fechaInicioTrabajo;
    }

    public LocalDate getFechaFinTrabajo() {
        return fechaFinTrabajo;
    }

    public void setFechaFinTrabajo(LocalDate fechaFinTrabajo) {
        this.fechaFinTrabajo = fechaFinTrabajo;
    }
}
