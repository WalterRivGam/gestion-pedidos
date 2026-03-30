package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.salida;

import org.springframework.stereotype.Component;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoIdempotencia;
import tecnica.prueba.gestion_pedidos.dominio.modelo.CargaIdempotencia;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.excepcion.ExcepcionSolicitud;
import tecnica.prueba.gestion_pedidos.infraestructura.mapper.CargaIdempotenciaMapper;
import tecnica.prueba.gestion_pedidos.infraestructura.repositorio.RepositorioIdempotencia;

@Component
public class AdaptadorIdempotencia implements PuertoIdempotencia {

    private final RepositorioIdempotencia repositorioIdempotencia;

    public AdaptadorIdempotencia(RepositorioIdempotencia repositorioIdempotencia) {
        this.repositorioIdempotencia = repositorioIdempotencia;
    }

    @Override
    public void verificarNoExistencia(String idempotencyKey, String archivoHash) {
        boolean existeIdempotencyKey = !repositorioIdempotencia.findByIdempotencyKey(idempotencyKey).isEmpty();
        boolean existeHash = !repositorioIdempotencia.findByHashArchivo(archivoHash).isEmpty();

        if (existeIdempotencyKey && existeHash) {
            throw new ExcepcionSolicitud("Solicitud duplicada", TipoError.SOLICITUD_DUPLICADA);
        } else if (existeIdempotencyKey) {
            throw new ExcepcionSolicitud("Intento de procesar otro archivo con la misma idempotency key.",
                    TipoError.IDEMPOTENCE_KEY_DUPLICADA);
        } else if (existeHash) {
            throw new ExcepcionSolicitud("Intento de procesar archivo ya procesado con otra idempotency key.",
                    TipoError.ARCHIVO_DUPLICADO);
        }
    }

    @Override
    public void guardar(CargaIdempotencia cargaIdempotencia) {
        repositorioIdempotencia.save(CargaIdempotenciaMapper.aEntidad(cargaIdempotencia));
    }
}
