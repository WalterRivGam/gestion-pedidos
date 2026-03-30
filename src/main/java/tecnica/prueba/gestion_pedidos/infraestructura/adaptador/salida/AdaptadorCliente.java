package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoCliente;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.ClienteMapper;
import tecnica.prueba.gestion_pedidos.infraestructura.repositorio.RepositorioCliente;

import java.util.Optional;

@Component
public class AdaptadorCliente implements PuertoCliente {

    RepositorioCliente repositorioCliente;

    public AdaptadorCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(String id) {
        return repositorioCliente.findById(id).map(ClienteMapper::entidadClienteACliente);
    }
}
