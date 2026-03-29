package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.infraestructura.dto.FilaCruda;

import java.io.InputStream;
import java.util.function.BiConsumer;

public interface PuertoProcesarArchivo {
    public void procesarArchivo(InputStream archivo, BiConsumer<FilaCruda, Integer> procesarPedido);
}
