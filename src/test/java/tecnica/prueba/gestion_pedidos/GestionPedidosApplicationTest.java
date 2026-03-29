package tecnica.prueba.gestion_pedidos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.dominio.servicio.ServicioValidadorPedidoDominio;

@SpringBootTest
class GestionPedidosApplicationTest {

	private ServicioValidadorPedidoDominio validador;

	@BeforeEach
	void setup() {
		validador = new ServicioValidadorPedidoDominio();
	}

	@Test
	void dado_numeroPedidoAlfanumerico_cuando_seValida_entonces_noLanzaExcepcion() {
		Pedido pedido = new Pedido();
		pedido.setNumeroPedido("P001");
	}

}
