package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoCliente;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCliente;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.ClienteMapper;
import tecnica.prueba.gestion_pedidos.infraestructura.repositorio.RepositorioCliente;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<String> obtenerClientesExistentes(Set<String> loteIdsCLiente) {
        return repositorioCliente.findByIdIn(loteIdsCLiente).stream()
                .map(EntidadCliente::getId).collect(Collectors.toList());
    }
}
