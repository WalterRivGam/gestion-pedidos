package tecnica.prueba.gestion_pedidos.infraestructura.mapper;

import tecnica.prueba.gestion_pedidos.dominio.modelo.CargaIdempotencia;
import tecnica.prueba.gestion_pedidos.infraestructura.entidad.EntidadCargaIdempotencia;

public class CargaIdempotenciaMapper {
    public static EntidadCargaIdempotencia aEntidad(CargaIdempotencia cargaIdempotencia) {
        EntidadCargaIdempotencia entidadCargaIdempotencia = new EntidadCargaIdempotencia();
        entidadCargaIdempotencia.setId(cargaIdempotencia.getId());
        entidadCargaIdempotencia.setIdempotencyKey(cargaIdempotencia.getIdempotencyKey());
        entidadCargaIdempotencia.setHashArchivo(cargaIdempotencia.getArchivoHash());
        entidadCargaIdempotencia.setCreatedAt(cargaIdempotencia.getCreatedAt());

        return entidadCargaIdempotencia;
    }
}
