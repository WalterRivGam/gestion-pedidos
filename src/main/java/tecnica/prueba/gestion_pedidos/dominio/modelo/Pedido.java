package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Pedido {
    private UUID id;
    private String numeroPedido;
    private String clienteId;
    private String zonaId;
    private LocalDate fechaEntrega;
    private Estado estado;
    private boolean requiereRefrigeracion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
