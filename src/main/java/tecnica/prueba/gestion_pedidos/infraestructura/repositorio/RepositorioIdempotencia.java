package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCargaIdempotencia;

import java.util.List;
import java.util.UUID;

public interface RepositorioIdempotencia extends JpaRepository<EntidadCargaIdempotencia, UUID> {

    List<EntidadCargaIdempotencia> findByIdempotencyKey(String idempotencyKey);

    List<EntidadCargaIdempotencia> findByHashArchivo(String hashArchivo);
}
