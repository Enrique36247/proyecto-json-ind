package com.example;

/**
 * Modelo de datos para Robots del Laboratorio
 * Temática: Gestión de Robots en un Laboratorio de Investigación
 * 
 */
public class Robot {
    private String id;
    private String nombre;
    private String modelo;
    private String funcion;
    private int añoFabricacion;
    private String estado; // ACTIVO, MANTENIMIENTO, INACTIVO
    private String ubicacion;

    public Robot() {
    }

    public Robot(String id, String nombre, String modelo, String funcion, 
                int añoFabricacion, String estado, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.funcion = funcion;
        this.añoFabricacion = añoFabricacion;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAñoFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' +
                ", funcion='" + funcion + '\'' +
                ", añoFabricacion=" + añoFabricacion +
                ", estado='" + estado + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}