package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Zona;

import java.util.Optional;

public interface PuertoZona {
    Optional<Zona> buscarZonaPorId(String zonaId);
}
