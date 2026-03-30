package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoIdempotencia;

@Component
public class AdaptadorIdempotencia implements PuertoIdempotencia {
    @Override
    public void verificarNoExistencia(String idempotencyKey, String archivoHash) {

    }
}
