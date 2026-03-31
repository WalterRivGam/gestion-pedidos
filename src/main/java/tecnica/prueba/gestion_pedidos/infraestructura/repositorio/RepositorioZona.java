package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadZona;

import java.util.List;
import java.util.Set;

public interface RepositorioZona extends JpaRepository<EntidadZona, String> {
    List<EntidadZona> findByIdIn(Set<String> ids);
}
