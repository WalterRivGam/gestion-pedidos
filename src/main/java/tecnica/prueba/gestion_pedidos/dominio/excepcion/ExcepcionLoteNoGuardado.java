package tecnica.prueba.gestion_pedidos.dominio.excepcion;

public class ExcepcionLoteNoGuardado extends RuntimeException {
    public ExcepcionLoteNoGuardado(String message) {
        super(message);
    }
}
