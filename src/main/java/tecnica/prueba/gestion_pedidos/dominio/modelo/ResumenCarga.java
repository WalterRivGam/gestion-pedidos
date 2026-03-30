package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResumenCarga {
    private int totalProcesados;
    private int guardados;
    private int conError;
    private List<ErrorPedido> errores;
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
