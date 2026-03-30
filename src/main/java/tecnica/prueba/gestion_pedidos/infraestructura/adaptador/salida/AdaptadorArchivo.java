package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoArchivo;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.FilaCruda;

import java.io.InputStream;
import java.util.function.BiConsumer;

@Component
public class AdaptadorArchivo implements PuertoArchivo {
    @Override
    public void procesarArchivo(InputStream archivo, BiConsumer<FilaCruda, Integer> procesarPedido) {

    }
}
