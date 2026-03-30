package tecnica.prueba.gestion_pedidos.infraestructura.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "cargas_idempotencia")
@Data
public class EntidadIdempotencia {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "archivo_hash")
    private String hashArchivo;

}
