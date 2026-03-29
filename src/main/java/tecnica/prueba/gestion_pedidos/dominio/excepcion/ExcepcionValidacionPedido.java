package tecnica.prueba.gestion_pedidos.dominio.excepcion;

import lombok.Getter;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

public class ExcepcionValidacionPedido extends RuntimeException {

    public ExcepcionValidacionPedido(TipoError tipoError) {
        super(tipoError.toString());
    }
}
