package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuertoCliente {
    Optional<Cliente> buscarClientePorId(String id);

    List<String> obtenerClientesExistentes(Set<String> loteIdsCLiente);
}
