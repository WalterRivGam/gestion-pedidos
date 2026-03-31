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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResumenCarga cargarPedidos(InputStream archivo, int tamanioLote) {
        ResumenCarga resumenCarga = new ResumenCarga();

        List<Pedido> lotePedidosEnMemoria = new ArrayList<>(tamanioLote);
        Set<String> loteIdsCLiente = new HashSet<>();
        Set<String> loteIdsZona = new HashSet<>();

        int[] filaFinDeLote = {0};

        puertoArchivo.procesarArchivo(archivo, resumenCarga, (PedidoSinValidar pedidoSinValidar, Integer numFila) -> {
            try {
                Pedido pedido = PedidoMapper.aDominio(pedidoSinValidar);

                validadorDominio.validar(pedido);

                loteIdsCLiente.add(pedido.getClienteId());
                loteIdsZona.add(pedido.getZonaId());

                pedido.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Lima")));
                pedido.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Lima")));
                lotePedidosEnMemoria.add(pedido);

                if (lotePedidosEnMemoria.size() == tamanioLote) {

                    List<String> idsClientesExistentes = puertoCliente.obtenerClientesExistentes(loteIdsCLiente);
                    List<String> idsZonasExistentes = puertoZona.obtenerZonasExistentes(loteIdsZona);

                    List<Pedido> lotePedidosValidos = lotePedidosEnMemoria.stream().filter(pedidoEnMem -> {
                        String clienteId = pedidoEnMem.getClienteId();
                        String zonaId = pedidoEnMem.getZonaId();

                        if(!idsClientesExistentes.contains(clienteId)) {
                            resumenCarga.incrementarConError(1);
                            resumenCarga.agregarError(new ErrorPedido(-1, TipoError.CLIENTE_NO_ENCONTRADO,
                                    "Cliente con id " + clienteId + " no encontrado."));
                        }

                        if(!idsZonasExistentes.contains(zonaId)) {
                            resumenCarga.incrementarConError(1);
                            resumenCarga.agregarError(new ErrorPedido(-1, TipoError.ZONA_INVALIDA,
                                    "Zona con id " + zonaId + " no encontrada."));
                        }

                        return (idsClientesExistentes.contains(clienteId) && idsZonasExistentes.contains(zonaId));
                    }).collect(Collectors.toList());

                    filaFinDeLote[0] = numFila;
                    try {
                        puertoPedido.guardarLote(lotePedidosValidos);
                        resumenCarga.incrementarGuardados(lotePedidosValidos.size());
                    } catch (Exception e) {
                        String mensajeError = String.format("No se guardó lote. Fila inicial: %d. Fila final: %d",
                                numFila - tamanioLote + 1, numFila);
                        throw new ExcepcionLoteNoGuardado(mensajeError);
                    } finally {
                        lotePedidosValidos.clear();
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
                List<String> idsClientesExistentes = puertoCliente.obtenerClientesExistentes(loteIdsCLiente);
                List<String> idsZonasExistentes = puertoZona.obtenerZonasExistentes(loteIdsZona);

                List<Pedido> lotePedidosValidos = lotePedidosEnMemoria.stream().filter(pedidoEnMem -> {
                    String clienteId = pedidoEnMem.getClienteId();
                    String zonaId = pedidoEnMem.getZonaId();

                    if(!idsClientesExistentes.contains(clienteId)) {
                        resumenCarga.incrementarConError(1);
                        resumenCarga.agregarError(new ErrorPedido(-1, TipoError.CLIENTE_NO_ENCONTRADO,
                                "Cliente con id " + clienteId + " no encontrado."));
                    }

                    if(!idsZonasExistentes.contains(zonaId)) {
                        resumenCarga.incrementarConError(1);
                        resumenCarga.agregarError(new ErrorPedido(-1, TipoError.ZONA_INVALIDA,
                                "Zona con id " + zonaId + " no encontrada."));
                    }

                    return (idsClientesExistentes.contains(clienteId) && idsZonasExistentes.contains(zonaId));
                }).collect(Collectors.toList());

                puertoPedido.guardarLote(lotePedidosValidos);
                resumenCarga.incrementarGuardados(lotePedidosValidos.size());
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
