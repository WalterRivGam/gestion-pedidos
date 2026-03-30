package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.entrada.excepcion;

public class ExcepcionErrorArchivo extends RuntimeException{
    public ExcepcionErrorArchivo(String mensaje) {
        super(mensaje);
    }
}
