package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CargaIdempotencia {
    private UUID id;
    private String idempotencyKey;
    private String archivoHash;
    private LocalDateTime createdAt;
}
