package com.empresa.restaurant_onion_architecture.infrastructure.persistence;

import com.empresa.restaurant_onion_architecture.domain.Pedido;
import com.empresa.restaurant_onion_architecture.domain.interfaces.IPedidoRepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MySQLPedidoRepository implements IPedidoRepository {

    // TODO: Implementar conexión a base de datos MySQL (H2 o MySQL real)
    private Map<Long, Pedido> database = new HashMap<>();
    private Long sequence = 1L;

    @Override
    public void guardar(Pedido pedido) {
        if(pedido == null) return;
        if(pedido.getId() == null) {
            pedido.setId(sequence);
            sequence++;
        }
        database.put(pedido.getId(), pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return database.get(id);
    }
}