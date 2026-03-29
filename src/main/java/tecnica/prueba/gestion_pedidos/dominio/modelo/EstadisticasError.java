package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EstadisticasError {
    private int total;
    private List<Integer> numerosDeLinea;
}
