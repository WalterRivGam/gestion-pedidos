package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.CargaIdempotencia;

public interface PuertoIdempotencia {
    public void verificarNoExistencia(String idempotencyKey, String archivoHash);

    public void guardar(CargaIdempotencia cargaIdempotencia);
}
