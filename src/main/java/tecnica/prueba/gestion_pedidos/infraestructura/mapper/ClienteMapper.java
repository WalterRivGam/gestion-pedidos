package tecnica.prueba.gestion_pedidos.infraestructura.mapper;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Cliente;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCliente;

public class ClienteMapper {
    public static Cliente entidadClienteACliente(EntidadCliente entidad) {
        return new Cliente(entidad.getId(), entidad.isActivo());
    }
}
