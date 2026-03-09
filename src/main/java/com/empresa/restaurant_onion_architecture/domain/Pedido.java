package com.empresa.restaurant_onion_architecture.domain;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private Long id;
    private List<Plato> platos = new ArrayList<>();
    private boolean confirmado = false;

    public void agregarPlato(Plato plato) {
        if(confirmado){
            throw new RuntimeException("El pedido ya está confirmado");
        }
        platos.add(plato);
    }

    public void confirmar(){
        if(platos.isEmpty()){
            throw new RuntimeException("No se puede confirmar un pedido vacío");
        }
        confirmado = true;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}