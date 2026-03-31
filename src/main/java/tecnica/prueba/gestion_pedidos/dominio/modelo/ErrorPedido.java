package tecnica.prueba.gestion_pedidos.dominio.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error al intentar procesar un pedido")
public class ErrorPedido {

    @Schema(description = "Número de línea donde ocurrió el error o -1 si no está asociado a una línea específica", example = "135")
    private int numeroLinea;

    @Schema(description = "Tipo de error", example = "CLIENTE_NO_ENCONTRADO")
    private TipoError tipoError;

    @Schema(description = "Motivo del error", example = "Cliente con id CLI-978 no encontrado")
    private String motivo;
}
