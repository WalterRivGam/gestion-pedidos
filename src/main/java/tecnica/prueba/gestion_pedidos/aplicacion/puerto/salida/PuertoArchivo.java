package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.PedidoSinValidar;

import java.io.InputStream;
import java.util.function.BiConsumer;

public interface PuertoArchivo {
    public void procesarArchivo(InputStream archivo, ResumenCarga resumenCarga, BiConsumer<PedidoSinValidar, Integer> procesarPedido);
}
