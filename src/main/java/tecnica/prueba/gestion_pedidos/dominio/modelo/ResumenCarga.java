package tecnica.prueba.gestion_pedidos.dominio.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "Objeto que representa un resumen del resultado de procesar el archivo CSV con los pedidos")
public class ResumenCarga {

    @Schema(description = "Total de líneas procesadas sin incluir líneas en blanco o el encabezado", example = "500")
    private int totalProcesados;

    @Schema(description = "Total de pedidos guardados", example = "495")
    private int guardados;

    @Schema(description = "Total de pedidos con error", example = "5")
    private int conError;

    @Schema(description = "Lista de errores")
    private List<ErrorPedido> errores;

    @Schema(description = "Errores agrupados por tipo")
    private Map<TipoError, EstadisticasError> erroresAgrupados;

    public ResumenCarga() {
        errores = new ArrayList<>();
        erroresAgrupados = new HashMap<>();
    }

    public void agruparErrores() {

        for (TipoError tipoError : TipoError.values()) {
            erroresAgrupados.put(tipoError, new EstadisticasError(0, new ArrayList<>()));
        }
        for (ErrorPedido errorFila : errores) {
            EstadisticasError estadisticasError = erroresAgrupados.get(errorFila.getTipoError());
            estadisticasError.setTotal(estadisticasError.getTotal() + 1);
            estadisticasError.getNumerosDeLinea().add(errorFila.getNumeroLinea());
        }
    }

    public void incrementarTotalProcesados() {
        totalProcesados += 1;
    }

    public void incrementarConError(int numConError) {
        conError += numConError;
    }

    public void incrementarGuardados(int numGuardados) {
        guardados += numGuardados;
    }

    public void agregarError(ErrorPedido errorFila) {
        errores.add(errorFila);
    }
}
