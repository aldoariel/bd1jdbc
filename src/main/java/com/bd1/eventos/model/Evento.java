package com.bd1.eventos.model;

import java.util.Date;

public class Evento {

    private Integer id;

    private String nombre;

    private Date fecha;

    public Evento() {}

    public Evento(Integer id, String nombre, Date data) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Evento other = (Evento) obj;

        if (id == null || other.id == null) {
            return false;
        }

        return id.equals(other.id);
    }
}
