package tecnica.prueba.gestion_pedidos.infraestructura.mapper;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Zona;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadZona;

public class ZonaMapper {
    public static Zona aDominio(EntidadZona entidadZona) {
        return new Zona(entidadZona.getId(), entidadZona.isSoporteRefrigeracion());
    }
}
