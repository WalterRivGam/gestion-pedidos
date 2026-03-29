package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

public interface PuertoIdempotencia {
    public void verificarNoExistencia(String idempotencyKey, String archivoHash);
}
