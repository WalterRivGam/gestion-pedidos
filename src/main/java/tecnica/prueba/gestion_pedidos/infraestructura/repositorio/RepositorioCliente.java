package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCliente;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, String> {
}
