package com.empresa.restaurant_onion_architecture.domain.interfaces;

import com.empresa.restaurant_onion_architecture.domain.Pedido;

public interface IPedidoRepository {

    void guardar(Pedido pedido);

    Pedido buscarPorId(Long id);
    
}
