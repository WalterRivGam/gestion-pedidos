package tecnica.prueba.gestion_pedidos.infraestructura.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(ExcepcionErrorArchivo.class)
    public ResponseEntity<ResumenCarga> excepcionErrorArchivo(ExcepcionErrorArchivo e) {
        ResumenCarga resumenCarga = new ResumenCarga();
        resumenCarga.agregarError(new ErrorPedido(e.getNumLinea(), TipoError.ERROR_LECTURA_ARCHIVO, e.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resumenCarga);
    }

    @ExceptionHandler(ExcepcionSolicitud.class)
    public ResponseEntity<ResumenCarga> excepcionSolicitud(ExcepcionSolicitud e) {
        ResumenCarga resumenCarga = new ResumenCarga();
        resumenCarga.agregarError(new ErrorPedido(-1, e.getTipoError(), e.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resumenCarga);
    }
}
