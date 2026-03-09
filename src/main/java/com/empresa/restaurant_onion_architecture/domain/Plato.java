package com.empresa.restaurant_onion_architecture.domain;

public class Plato {

    private String nombre;
    private double precio;

    public Plato(String nombre, double precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
