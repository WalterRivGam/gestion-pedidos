package tecnica.prueba.gestion_pedidos.dominio.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Estadísticas asociadas a un tipo de error")
public class EstadisticasError {
    @Schema(description = "Total de errores de un mismo tipo", example = "3")
    private int total;

    @Schema(description = "Números de línea de todos los errores de un mismo tipo", example = "[113, 58, 73]")
    private List<Integer> numerosDeLinea;
}
