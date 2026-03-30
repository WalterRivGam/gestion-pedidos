package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;

import java.util.List;

@Component
public class AdaptadorPedido implements PuertoPedido {
    @Override
    public void guardarLote(List<Pedido> pedidos) {

    }
}
