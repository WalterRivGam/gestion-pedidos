package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoCliente;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;

import java.util.Optional;

@Component
public class AdaptadorCliente implements PuertoCliente {
    @Override
    public Optional<Cliente> buscarClientePorId(String id) {
        return Optional.empty();
    }
}
