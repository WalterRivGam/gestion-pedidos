package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCliente;

import java.util.List;
import java.util.Set;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, String> {
    List<EntidadCliente> findByIdIn(Set<String> ids);
}
