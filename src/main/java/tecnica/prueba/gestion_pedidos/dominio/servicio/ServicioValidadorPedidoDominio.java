package tecnica.prueba.gestion_pedidos.dominio.servicio;

import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Estado;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

import java.time.LocalDate;
import java.time.ZoneId;

public class ServicioValidadorPedidoDominio {

    private static final ZoneId ZONA_LIMA = ZoneId.of("America/Lima");

    public void validar(Pedido pedido) {

        // valida número de pedido sea alfanumérico
        String numeroPedido = pedido.getNumeroPedido();
        boolean numeroPedidoValido = numeroPedido != null && numeroPedido.matches("^[a-zA-Z0-9]+$");
        if(!numeroPedidoValido) {
            throw new ExcepcionValidacionPedido(TipoError.NUMERO_PEDIDO_INVALIDO);
        }

        // valida fecha de entrega no sea pasada
        LocalDate fechaEntrega = pedido.getFechaEntrega();
        boolean fechaInvalida = fechaEntrega == null || fechaEntrega.isBefore(LocalDate.now(ZONA_LIMA));
        if(fechaInvalida) {
            throw new ExcepcionValidacionPedido(TipoError.FECHA_INVALIDA);
        }

    }

}
