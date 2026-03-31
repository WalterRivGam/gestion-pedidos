package tecnica.prueba.gestion_pedidos.dominio.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private List<ErrorPedido> erroresPedidos;

    @Schema(description = "Errores agrupados por tipo")
    private List<ErrorAgrupado> erroresAgrupados;

    public ResumenCarga() {
        erroresPedidos = new ArrayList<>();
        erroresAgrupados = new ArrayList<>();
    }

    public void agruparErrores() {
        erroresPedidos.forEach(errorPedido -> {
            TipoError tipoError = errorPedido.getTipoError();
            ErrorAgrupado errorAgrupado = erroresAgrupados.stream()
                    .filter(errorAgrup -> errorAgrup.getTipoError() == tipoError)
                    .findFirst().orElse(null);
            if(errorAgrupado == null) {
                List<Integer> numerosDeLinea = new ArrayList<>();
                numerosDeLinea.add(errorPedido.getNumeroLinea());
                erroresAgrupados.add(new ErrorAgrupado(errorPedido.getTipoError(), 1, numerosDeLinea));
            } else {
                errorAgrupado.setTotal(errorAgrupado.getTotal() + 1);
                errorAgrupado.getNumerosDeLinea().add(errorPedido.getNumeroLinea());
            }
        });
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
        erroresPedidos.add(errorFila);
    }
}
