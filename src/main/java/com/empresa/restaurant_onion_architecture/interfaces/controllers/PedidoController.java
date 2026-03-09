package com.empresa.restaurant_onion_architecture.interfaces.controllers;

import com.empresa.restaurant_onion_architecture.application.PedidoService;
import com.empresa.restaurant_onion_architecture.domain.Pedido;
import com.empresa.restaurant_onion_architecture.domain.Plato;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    public Pedido crearPedido(){
        return service.crearPedido();
    }

    @PostMapping("/{id}/platos")
    public Pedido agregarPlato(@PathVariable Long id, @RequestBody Plato plato){
        service.agregarPlato(id, plato);
        return service.obtenerPedido(id);
    }

    @PostMapping("/{id}/confirmar")
    public Pedido confirmar(@PathVariable Long id){
        service.confirmarPedido(id);
        return service.obtenerPedido(id);
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Long id){
        return service.obtenerPedido(id);
    }

}