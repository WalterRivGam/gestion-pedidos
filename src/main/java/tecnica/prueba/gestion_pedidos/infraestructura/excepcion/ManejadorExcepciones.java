package tecnica.prueba.gestion_pedidos.infraestructura.excepcion;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ManejadorExcepciones {

    private String getCorrelationId() {
        String correlationId = MDC.get("correlationId");
        return correlationId != null ? correlationId : "N/A";
    }

    @ExceptionHandler(ExcepcionErrorArchivo.class)
    public ResponseEntity<ApiErrorResponse> excepcionErrorArchivo(ExcepcionErrorArchivo e) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "ERROR_LECTURA_ARCHIVO",
                "Hubo un problema al intentar leer o procesar el archivo CSV.",
                List.of("Error en la línea " + e.getNumLinea() + ": " + e.getMessage()),
                getCorrelationId()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ExcepcionSolicitud.class)
    public ResponseEntity<ApiErrorResponse> excepcionSolicitud(ExcepcionSolicitud e) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getTipoError().name(),
                "La solicitud contiene datos inválidos o reglas de negocio incumplidas.",
                List.of(e.getMessage()),
                getCorrelationId()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> excepcionGlobal(Exception e) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Ha ocurrido un error inesperado en el servidor. Por favor, contacte a soporte usando el Correlation ID.",
                Collections.emptyList(),
                getCorrelationId()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}