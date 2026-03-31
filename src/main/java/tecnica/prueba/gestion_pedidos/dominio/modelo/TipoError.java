package tecnica.prueba.gestion_pedidos.dominio.modelo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de error")
public enum TipoError {
    NUMERO_PEDIDO_INVALIDO,
    FECHA_INVALIDA,
    ESTADO_INVALIDO,
    SOLICITUD_DUPLICADA,
    ARCHIVO_DUPLICADO,
    IDEMPOTENCE_KEY_DUPLICADA,
    CADENA_FRIO_NO_SOPORTADA,
    CLIENTE_NO_ENCONTRADO,
    ZONA_INVALIDA,
    LOTE_NO_GUARDADO,
    ERROR_LECTURA_ARCHIVO,
    ERROR_FORMATO_LINEA,
    OTRO
}
