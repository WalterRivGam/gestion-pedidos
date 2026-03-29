package tecnica.prueba.gestion_pedidos.dominio.modelo;

public enum TipoError {
    NUMERO_PEDIDO_INVALIDO,
    FECHA_INVALIDA,
    ESTADO_INVALIDO,
    PEDIDO_DUPLICADO,
    ARCHIVO_DUPLICADO,
    IDEMPOTENCE_KEY_DUPLICADA,
    REQUIERE_REFRIGERACION_INVALIDO,
    OTRO
}
