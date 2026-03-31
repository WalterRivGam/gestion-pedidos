package tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida;

import tecnica.prueba.gestion_pedidos.dominio.modelo.Zona;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuertoZona {
    Optional<Zona> buscarZonaPorId(String zonaId);

    List<String> obtenerZonasExistentes(Set<String> loteIdsZona);
}
