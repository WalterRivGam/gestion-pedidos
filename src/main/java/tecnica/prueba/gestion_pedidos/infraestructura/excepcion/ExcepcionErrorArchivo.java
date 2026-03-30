package tecnica.prueba.gestion_pedidos.infraestructura.excepcion;

import lombok.Getter;

@Getter
public class ExcepcionErrorArchivo extends RuntimeException{

    private int numLinea = -1;

    public ExcepcionErrorArchivo(String mensaje) {
        super(mensaje);
    }

    public ExcepcionErrorArchivo(String mensaje, int numLinea) {
        super(mensaje);
        this.numLinea = numLinea;
    }

}
