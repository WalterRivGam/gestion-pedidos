package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Pedido;

import java.util.List;

public interface PuertoPedido {
    public void guardarLote(List<Pedido> pedidos);
}
