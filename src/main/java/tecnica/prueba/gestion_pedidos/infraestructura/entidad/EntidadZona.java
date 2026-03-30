package tecnica.prueba.gestion_pedidos.infraestructura.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "zonas")
@Data
public class EntidadZona {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "soporte_refrigeracion")
    private boolean soporteRefrigeracion;
}
