package tecnica.prueba.gestion_pedidos.dominio.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResumenCarga {
    private int totalProcesados;
    private int guardados;
    private int conError;
    private List<ErrorFila> errores;
    private Map<TipoError, EstadisticasError>  erroresAgrupados;

    public void agruparErrores() {
        erroresAgrupados = new HashMap<>();

        for(TipoError tipoError: TipoError.values()) {
            erroresAgrupados.put(tipoError, new EstadisticasError(0, new ArrayList<>()));
        }
        for(ErrorFila errorFila: errores) {
            EstadisticasError estadisticasError = erroresAgrupados.get(errorFila.getMotivo());
            estadisticasError.setTotal(estadisticasError.getTotal() + 1);
            estadisticasError.getNumerosDeLinea().add(errorFila.getNumeroLinea());
        }
    }
}
