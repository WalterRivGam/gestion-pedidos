package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorFila {
    private int numeroLinea;
    private TipoError motivo;
}
