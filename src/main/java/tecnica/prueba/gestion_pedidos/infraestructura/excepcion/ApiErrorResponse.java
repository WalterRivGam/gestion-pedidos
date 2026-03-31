package tecnica.prueba.gestion_pedidos.infraestructura.excepcion;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Modelo estandarizado para la devolución de errores de la API.")
public class ApiErrorResponse {

    @Schema(description = "Código de error interno del negocio", example = "ERROR_LECTURA_ARCHIVO")
    private String code;

    @Schema(description = "Mensaje amigable y descriptivo del error", example = "El archivo enviado no tiene un formato válido.")
    private String message;

    @Schema(description = "Lista de detalles específicos o validaciones que fallaron")
    private List<String> details;

    @Schema(description = "ID de rastreo para buscar el error en los logs", example = "550e8400-e29b-41d4-a716-446655440000")
    private String correlationId;

    public ApiErrorResponse(String code, String message, List<String> details, String correlationId) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.correlationId = correlationId;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public List<String> getDetails() { return details; }
    public String getCorrelationId() { return correlationId; }
}
