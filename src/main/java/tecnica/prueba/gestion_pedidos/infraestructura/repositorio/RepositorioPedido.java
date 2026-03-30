package tecnica.prueba.gestion_pedidos.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCargaIdempotencia;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadPedido;

import java.util.UUID;

public interface RepositorioPedido extends JpaRepository<EntidadPedido, UUID> {
}
