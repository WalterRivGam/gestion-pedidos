package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoZona;
import tecnica.prueba.gestion_pedidos.dominio.modelo.Zona;

import java.util.Optional;

@Component
public class AdaptadorZona implements PuertoZona {
    @Override
    public Optional<Zona> buscarZonaPorId(String zonaId) {
        return Optional.empty();
    }
}
