package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoArchivo;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.adaptador.entrada.excepcion.ExcepcionErrorArchivo;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.PedidoSinValidar;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.PedidoMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

@Component
public class AdaptadorArchivoCsv implements PuertoArchivo {
    @Override
    public void procesarArchivo(InputStream archivoCsv, ResumenCarga resumenCarga, BiConsumer<PedidoSinValidar, Integer> procesarPedido) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivoCsv, StandardCharsets.UTF_8))) {

            String linea;
            int numLinea = 1;

            try {
                while ((linea = reader.readLine()) != null) {
                    if (numLinea == 1) {
                        numLinea++;
                        continue;
                    }
                    if (linea.trim().isBlank()) {
                        continue;
                    }
                    resumenCarga.incrementarTotalProcesados();

                    String[] columnas = linea.split(",");

                    if (columnas.length < 6) {
                        resumenCarga.incrementarConError(1);
                        resumenCarga.agregarError(new ErrorPedido(numLinea, TipoError.ERROR_FORMATO_LINEA,
                                "La fila no tiene todas las columnas requeridas."));
                    }
                    PedidoSinValidar pedidoSinValidar = PedidoMapper.aPedidoSinValidar(columnas);

                    procesarPedido.accept(pedidoSinValidar, numLinea);

                    numLinea++;
                }
            } catch (IOException e) {
                throw new ExcepcionErrorArchivo("Error al intentar leer linea del" +
                        "archivo. No se procesarán las siguientes líneas del archivo.", numLinea);
            }
        } catch (IOException e) {
            throw new ExcepcionErrorArchivo("Excepción I/O al leer el archivo CSV");
        }
    }
}
