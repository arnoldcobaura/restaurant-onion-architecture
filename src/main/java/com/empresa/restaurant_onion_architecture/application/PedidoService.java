package com.empresa.restaurant_onion_architecture.application;

import com.empresa.restaurant_onion_architecture.domain.Pedido;
import com.empresa.restaurant_onion_architecture.domain.Plato;
import com.empresa.restaurant_onion_architecture.domain.interfaces.IPedidoRepository;

import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final IPedidoRepository repository;

    public PedidoService(IPedidoRepository repository){
        this.repository = repository;
    }

    public Pedido crearPedido(){
        Pedido pedido = new Pedido();
        repository.guardar(pedido);
        return pedido;
    }

    public void agregarPlato(Long id, Plato plato){
        Pedido pedido = repository.buscarPorId(id);
        if(pedido == null) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        pedido.agregarPlato(plato);
        repository.guardar(pedido);
    }

    public void confirmarPedido(Long id){
        Pedido pedido = repository.buscarPorId(id);
        if(pedido == null) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        pedido.confirmar();
        repository.guardar(pedido);
    }
    
    public Pedido obtenerPedido(Long id){
        Pedido pedido = repository.buscarPorId(id);
        if(pedido == null) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        return pedido;
    }

}