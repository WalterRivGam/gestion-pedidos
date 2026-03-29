package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;

import java.util.Optional;

public interface PuertoCliente {
    Optional<Cliente> buscarClientePorId(String id);
}
