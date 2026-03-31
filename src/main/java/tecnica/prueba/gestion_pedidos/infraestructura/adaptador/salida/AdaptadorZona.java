package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoZona;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Zona;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadZona;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.ZonaMapper;
import tecnica.prueba.gestion_pedidos.infraestructura.repositorio.RepositorioZona;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdaptadorZona implements PuertoZona {
    private final RepositorioZona respositorioZona;

    public AdaptadorZona(RepositorioZona respositorioZona) {
        this.respositorioZona = respositorioZona;
    }

    @Override
    public Optional<Zona> buscarZonaPorId(String zonaId) {
        return respositorioZona.findById(zonaId).map(ZonaMapper::aDominio);
    }

    @Override
    public List<Zona> obtenerZonasExistentes(Set<String> loteIdsZona) {
        return respositorioZona.findByIdIn(loteIdsZona).stream()
                .map(ZonaMapper::aDominio).collect(Collectors.toList());
    }
}
