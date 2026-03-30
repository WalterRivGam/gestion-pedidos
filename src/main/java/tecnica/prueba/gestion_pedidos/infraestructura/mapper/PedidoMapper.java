package tecnica.prueba.gestion_pedidos.infraestructura.mapper;

import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Estado;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.PedidoSinValidar;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadPedido;

import java.time.LocalDate;
import java.util.UUID;

public class PedidoMapper {

    public static Pedido aDominio(PedidoSinValidar pedidoSinValidar) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setNumeroPedido(pedidoSinValidar.getNumeroPedido());
        pedido.setClienteId(pedidoSinValidar.getClienteId());
        pedido.setZonaId(pedidoSinValidar.getZonaEntrega());
        pedido.setFechaEntrega(LocalDate.parse(pedidoSinValidar.getFechaEntrega()));
        pedido.setEstado(Estado.valueOf(pedidoSinValidar.getEstado()));
        String requiereRefrigeracion = pedidoSinValidar.getRequiereRefrigeracion().trim().toLowerCase();
        if (requiereRefrigeracion.equals("true") || requiereRefrigeracion.equals("false")) {
            pedido.setRequiereRefrigeracion(Boolean.parseBoolean(requiereRefrigeracion));
        } else {
            throw new ExcepcionValidacionPedido(TipoError.REQUIERE_REFRIGERACION_INVALIDO);
        }
        return pedido;
    }

    public static EntidadPedido aEntidad(Pedido pedido) {
        EntidadPedido entidadPedido = new EntidadPedido();
        entidadPedido.setId(pedido.getId());
        entidadPedido.setNumeroPedido(pedido.getNumeroPedido());
        entidadPedido.setClienteId(pedido.getClienteId());
        entidadPedido.setZonaId(pedido.getZonaId());
        entidadPedido.setFechaEntrega(pedido.getFechaEntrega());
        entidadPedido.setEstado(pedido.getEstado());
        entidadPedido.setRequiereRefrigeracion(pedido.isRequiereRefrigeracion());
        entidadPedido.setCreatedAt(pedido.getCreatedAt());
        entidadPedido.setUpdatedAt(pedido.getUpdatedAt());
        return entidadPedido;
    }

    public static PedidoSinValidar aPedidoSinValidar(String[] columnas) {
        PedidoSinValidar pedidoSinValidar = new PedidoSinValidar();
        pedidoSinValidar.setNumeroPedido(columnas[0]);
        pedidoSinValidar.setClienteId(columnas[1]);
        pedidoSinValidar.setFechaEntrega(columnas[2]);
        pedidoSinValidar.setEstado((columnas[3]));
        pedidoSinValidar.setZonaEntrega(columnas[4]);
        pedidoSinValidar.setRequiereRefrigeracion(columnas[5]);

        return pedidoSinValidar;
    }
}
