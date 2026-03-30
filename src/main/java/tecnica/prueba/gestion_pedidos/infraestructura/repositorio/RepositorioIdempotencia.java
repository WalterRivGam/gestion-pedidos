package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadIdempotencia;

import java.util.List;
import java.util.UUID;

public interface RepositorioIdempotencia extends JpaRepository<EntidadIdempotencia, UUID> {

    List<EntidadIdempotencia> findByIdempotencyKey(String idempotencyKey);

    List<EntidadIdempotencia> findByHashArchivo(String hashArchivo);
}
