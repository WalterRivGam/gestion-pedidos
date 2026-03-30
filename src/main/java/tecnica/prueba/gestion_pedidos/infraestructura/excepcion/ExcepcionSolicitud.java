package tecnica.prueba.gestion_pedidos.infraestructura.excepcion;

import lombok.Getter;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

@Getter
public class ExcepcionSolicitud extends RuntimeException {
    private TipoError tipoError;

    public ExcepcionSolicitud(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }
}
