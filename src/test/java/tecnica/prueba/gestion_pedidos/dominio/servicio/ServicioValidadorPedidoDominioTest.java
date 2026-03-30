package tecnica.prueba.gestion_pedidos.dominio.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tecnica.prueba.gestion_pedidos.dominio.excepcion.ExcepcionValidacionPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.*;

public class ServicioValidadorPedidoDominioTest {
    private ServicioValidadorPedidoDominio validador;
    private static final ZoneId ZONA_LIMA = ZoneId.of("America/Lima");
    LocalDate fechaHoy;
    Pedido pedido;

    @BeforeEach
    void setup() {
        validador = new ServicioValidadorPedidoDominio();
        fechaHoy = LocalDate.now(ZONA_LIMA);
        pedido = new Pedido();
        pedido.setNumeroPedido("P001");
        pedido.setFechaEntrega(fechaHoy);
    }

    @Test
    void dado_pedidoValido_cuando_seValidaPedido_entonces_noLanzaExcepcion() {
        assertThatNoException().isThrownBy(() -> validador.validar(pedido));
    }

    @Test
    void dado_numeroPedidoNoAlfanumerico_cuando_seValidaPedido_entonces_lanzaExcepcion() {
        pedido.setNumeroPedido("$!&");

        assertThatExceptionOfType(ExcepcionValidacionPedido.class).isThrownBy(() -> validador.validar(pedido))
                .withMessage(TipoError.NUMERO_PEDIDO_INVALIDO.toString());
    }

    @Test
    void dado_numeroPedidoNull_cuando_seValidaPedido_entonces_lanzaExcepcion() {
        pedido.setNumeroPedido(null);

        assertThatExceptionOfType(ExcepcionValidacionPedido.class).isThrownBy(() -> validador.validar(pedido))
                .withMessage(TipoError.NUMERO_PEDIDO_INVALIDO.toString());
    }

    @Test
    void dado_fechaEntregaDeAyer_cuando_seValidaPedido_entonces_lanzaExcepcion() {;
        LocalDate fechaAyer = fechaHoy.minusDays(1);
        pedido.setFechaEntrega(fechaAyer);

        assertThatExceptionOfType(ExcepcionValidacionPedido.class).isThrownBy(() -> validador.validar(pedido))
                .withMessage(TipoError.FECHA_INVALIDA.toString());
    }

    @Test
    void dado_fechaEntregaNull_cuando_seValidaPedido_entonces_lanzaExcepcion() {;
        pedido.setFechaEntrega(null);

        assertThatExceptionOfType(ExcepcionValidacionPedido.class).isThrownBy(() -> validador.validar(pedido))
                .withMessage(TipoError.FECHA_INVALIDA.toString());
    }

}
