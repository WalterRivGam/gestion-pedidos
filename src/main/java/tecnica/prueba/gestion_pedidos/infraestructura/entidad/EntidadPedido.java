package tecnica.prueba.gestion_pedidos.infraestructura.entidad;

import jakarta.persistence.*;
import lombok.Data;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Estado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
@Data
public class EntidadPedido {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "numero_pedido")
    private String numeroPedido;

    @Column(name = "cliente_id")
    private String clienteId;

    @Column(name = "zona_id")
    private String zonaId;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "requiere_refrigeracion")
    private boolean requiereRefrigeracion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
