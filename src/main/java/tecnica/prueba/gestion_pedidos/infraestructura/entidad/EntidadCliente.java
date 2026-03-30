package tecnica.prueba.gestion_pedidos.infraestructura.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class EntidadCliente {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "activo")
    private boolean activo;
}
