package tecnica.prueba.gestion_pedidos.aplicacion.servicio;

import tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada.PuertoCargaPedidos;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.*;
import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionLoteNoGuardado;
import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.dominio.servicio.ServicioValidadorPedidoDominio;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.PedidoSinValidar;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.PedidoMapper;

import java.io.InputStream;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ServicioCargaPedidos implements PuertoCargaPedidos {

    PuertoIdempotencia puertoIdempotencia;
    PuertoArchivo puertoArchivo;
    PuertoCliente puertoCliente;
    PuertoZona puertoZona;
    PuertoPedido puertoPedido;
    ServicioValidadorPedidoDominio validadorDominio = new ServicioValidadorPedidoDominio();

    public ServicioCargaPedidos(PuertoIdempotencia puertoIdempotencia,
                                PuertoArchivo puertoArchivo,
                                PuertoCliente puertoCliente,
                                PuertoZona puertoZona,
                                PuertoPedido puertoPedido) {
        this.puertoIdempotencia = puertoIdempotencia;
        this.puertoArchivo = puertoArchivo;
        this.puertoCliente = puertoCliente;
        this.puertoZona = puertoZona;
        this.puertoPedido = puertoPedido;
    }

    @Override
    public ResumenCarga cargarPedidos(InputStream archivo, String idempotencyKey, String archivoHash, int tamanioLote) {
        ResumenCarga resumenCarga = new ResumenCarga();

        puertoIdempotencia.verificarNoExistencia(idempotencyKey, archivoHash);

        List<Pedido> lotePedidosEnMemoria = new ArrayList<>(tamanioLote);

        int[] filaFinDeLote = {0};

        puertoArchivo.procesarArchivo(archivo, resumenCarga, (PedidoSinValidar pedidoSinValidar, Integer numFila) -> {
            try {
                Pedido pedido = PedidoMapper.aDominio(pedidoSinValidar);

                validadorDominio.validar(pedido);

                puertoCliente.buscarClientePorId(pedido.getClienteId())
                        .orElseThrow(() -> new ExcepcionValidacionPedido(TipoError.CLIENTE_INVALIDO));

                puertoZona.buscarZonaPorId(pedido.getZonaId())
                        .orElseThrow(() -> new ExcepcionValidacionPedido(TipoError.ZONA_INVALIDA));

                lotePedidosEnMemoria.add(pedido);

                if (lotePedidosEnMemoria.size() == tamanioLote) {
                    filaFinDeLote[0] = numFila;
                    try {
                        puertoPedido.guardarLote(lotePedidosEnMemoria);
                        resumenCarga.incrementarGuardados(tamanioLote);
                    } catch (Exception e) {
                        String mensajeError = String.format("No se guardó lote. Fila inicial: %d. Fila final: %d",
                                numFila - tamanioLote + 1, numFila);
                        throw new ExcepcionLoteNoGuardado(mensajeError);
                    } finally {
                        lotePedidosEnMemoria.clear();
                    }
                }
            } catch (DateTimeParseException e) {
                resumenCarga.incrementarConError(1);
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.FECHA_INVALIDA, "Fecha inválida"));
            } catch (IllegalArgumentException e) {
                resumenCarga.incrementarConError(1);
                resumenCarga
                        .agregarError(new ErrorPedido(numFila, TipoError.ESTADO_INVALIDO, "Estado inválido"));
            } catch (ExcepcionValidacionPedido e) {
                resumenCarga.incrementarConError(1);
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.valueOf(e.getMessage()), e.getMessage()));
            } catch (ExcepcionLoteNoGuardado e) {
                resumenCarga.incrementarConError(tamanioLote);
                resumenCarga.agregarError(new ErrorPedido(-1, TipoError.LOTE_NO_GUARDADO, e.getMessage()));
            } catch (Exception e) {
                resumenCarga.agregarError(new ErrorPedido(numFila, TipoError.OTRO, e.getMessage()));
            }
        });

        if (!lotePedidosEnMemoria.isEmpty()) {
            try {
                puertoPedido.guardarLote(lotePedidosEnMemoria);
                resumenCarga.incrementarGuardados(lotePedidosEnMemoria.size());
            } catch (Exception e) {
                resumenCarga.incrementarConError(lotePedidosEnMemoria.size());
                String mensajeError = String.format("No se guardó lote. Fila inicial: %d. Fila final: %d",
                        filaFinDeLote[0], filaFinDeLote[0] + lotePedidosEnMemoria.size() - 1);
                resumenCarga.agregarError(new ErrorPedido(-1, TipoError.LOTE_NO_GUARDADO, mensajeError));
            } finally {
                lotePedidosEnMemoria.clear();
            }
        }

        resumenCarga.agruparErrores();
        return resumenCarga;
    }
}
