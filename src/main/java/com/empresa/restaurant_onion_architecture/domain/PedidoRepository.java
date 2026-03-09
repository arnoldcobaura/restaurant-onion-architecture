package com.empresa.restaurant_onion_architecture.domain;

public interface PedidoRepository {

    void guardar(Pedido pedido);

    Pedido buscarPorId(Long id);
    
}
