package tecnica.prueba.gestion_pedidos.dominio.excepcion;

import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

public class ExcepcionValidacionPedido extends RuntimeException {
    public ExcepcionValidacionPedido(TipoError tipoError) {
        super(tipoError.toString());
    }
}
