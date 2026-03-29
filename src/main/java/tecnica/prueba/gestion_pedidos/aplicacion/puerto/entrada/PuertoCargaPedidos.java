package tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada;

import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;

import java.io.InputStream;

public interface PuertoCargaPedidos {
    public ResumenCarga procesarArchivo(InputStream archivoCsv, String idempotencyKey);
}
