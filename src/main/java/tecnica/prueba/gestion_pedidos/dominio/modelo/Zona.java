package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Zona {
    private String id;
    private boolean soporteRefrigeracion;
}
