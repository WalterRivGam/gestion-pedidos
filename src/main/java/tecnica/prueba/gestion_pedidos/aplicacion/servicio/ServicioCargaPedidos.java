package tecnica.prueba.gestion_pedidos.aplicacion.servicio;

import tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada.PuertoCargaPedidos;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoIdempotencia;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoProcesarArchivo;
import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.FilaCruda;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.PedidoMapper;

import java.io.InputStream;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ServicioCargaPedidos implements PuertoCargaPedidos {

    PuertoIdempotencia puertoIdempotencia;
    PuertoProcesarArchivo puertoProcesarArchivo;

    public ServicioCargaPedidos(PuertoIdempotencia puertoIdempotencia, PuertoProcesarArchivo puertoProcesarArchivo) {
        this.puertoIdempotencia = puertoIdempotencia;
        this.puertoProcesarArchivo = puertoProcesarArchivo;
    }

    @Override
    public ResumenCarga cargarPedidos(InputStream archivo, String idempotencyKey, String archivoHash, int tamanioLote) {
        ResumenCarga resumenCarga = new ResumenCarga();

        puertoIdempotencia.verificarNoExistencia(idempotencyKey, archivoHash);

        List<Pedido> lotePedidosEnMemoria = new ArrayList<>(tamanioLote);

        puertoProcesarArchivo.procesarArchivo(archivo, (FilaCruda filaCruda, Integer numFila) -> {
            try {
                resumenCarga.incrementarTotalProcesados();

                Pedido pedido = PedidoMapper.aDominio(filaCruda);


            } catch (DateTimeParseException e) {
                resumenCarga.incrementarConError();
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.FECHA_INVALIDA, "FECHA_INVALIDA"));
            } catch (IllegalArgumentException e) {
                resumenCarga.incrementarConError();
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.ESTADO_INVALIDO, "ESTADO_INVALIDA"));
            } catch (ExcepcionValidacionPedido e) {
                resumenCarga.incrementarConError();
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.valueOf(e.getMessage()), e.getMessage()));
            }
        });

        resumenCarga.agruparErrores();
        return resumenCarga;
    }
}
