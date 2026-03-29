package tecnica.prueba.gestion_pedidos.infraestructura.mapper;

import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Estado;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.dto.FilaCruda;

import java.time.LocalDate;
import java.util.UUID;

public class PedidoMapper {
    public static Pedido aDominio(FilaCruda filaCruda) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setNumeroPedido(filaCruda.getNumeroPedido());
        pedido.setClienteId(filaCruda.getClienteId());
        pedido.setZonaId(filaCruda.getZonaEntrega());
        pedido.setFechaEntrega(LocalDate.parse(filaCruda.getFechaEntrega()));
        pedido.setEstado(Estado.valueOf(filaCruda.getEstado()));
        String requiereRefrigeracion = filaCruda.getRequiereRefrigeracion().trim().toLowerCase();
        if (requiereRefrigeracion.equals("true") || requiereRefrigeracion.equals("false")) {
            pedido.setRequiereRefrigeracion(Boolean.parseBoolean(requiereRefrigeracion));
        } else {
            throw new ExcepcionValidacionPedido(TipoError.REQUIERE_REFRIGERACION_INVALIDO);
        }
        return pedido;
    }
}
