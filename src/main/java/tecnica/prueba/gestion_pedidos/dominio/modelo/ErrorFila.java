package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorFila {
    private int numeroLinea;
    private TipoError motivo;
}
