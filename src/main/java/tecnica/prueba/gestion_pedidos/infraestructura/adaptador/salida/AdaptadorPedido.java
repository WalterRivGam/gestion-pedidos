package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadPedido;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.PedidoMapper;
import tecnica.prueba.gestion_pedidos.infraestructura.repositorio.RepositorioPedido;

import java.util.List;

@Component
public class AdaptadorPedido implements PuertoPedido {
    private final RepositorioPedido repositorioPedido;
    private final EntityManager entityManager;

    public AdaptadorPedido(RepositorioPedido repositorioPedido, EntityManager entityManager) {
        this.repositorioPedido = repositorioPedido;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void guardarLote(List<Pedido> pedidos) {
        List<EntidadPedido> lotePedidos = pedidos.stream().map(PedidoMapper::aEntidad).toList();

        repositorioPedido.saveAll(lotePedidos);
        repositorioPedido.flush();

        entityManager.clear();
    }
}
