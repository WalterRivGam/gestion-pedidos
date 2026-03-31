package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.entrada;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada.PuertoCargaPedidos;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.PuertoIdempotencia;
import tecnica.prueba.gestion_pedidos.dominio.modelo.CargaIdempotencia;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.infraestructura.excepcion.ExcepcionErrorArchivo;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Carga de pedidos", description = "Endpoints para la carga de pedidos")
public class ControladorPedido {

    @Value("${pedidos.tamanio.lote:500}")
    private int tamanioLote;

    private final PuertoCargaPedidos puertoCargaPedidos;
    private final PuertoIdempotencia puertoIdempotencia;

    public ControladorPedido(PuertoCargaPedidos puertoCargaPedidos, PuertoIdempotencia puertoIdempotencia) {
        this.puertoCargaPedidos = puertoCargaPedidos;
        this.puertoIdempotencia = puertoIdempotencia;
    }

    @Operation(summary = "Carga de pedidos", description = "Carga pedidos desde un archivo CSV, los valida y guarda")
    @PostMapping(value = "/cargar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumenCarga> cargarPedidos(
            @Parameter(description = "Clave de idempotencia para evitar solicitudes duplicadas")
            @RequestHeader("Idempotency-Key") String idempotencyKey,

            @Parameter(description = "Archivo CSV con la lista de pedidos")
            @RequestPart("file") MultipartFile file) {
        InputStream inputStream;
        String hash;
        try {
            inputStream = file.getInputStream();
            hash = DigestUtils.sha256Hex(inputStream);
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ExcepcionErrorArchivo("Excepción I/O al leer el archivo o al obtener su hash");
        }

        puertoIdempotencia.verificarNoExistencia(idempotencyKey, hash);
        CargaIdempotencia cargaIdempotencia = new CargaIdempotencia();
        cargaIdempotencia.setId(UUID.randomUUID());
        cargaIdempotencia.setIdempotencyKey(idempotencyKey);
        cargaIdempotencia.setArchivoHash(hash);
        cargaIdempotencia.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Lima")));
        puertoIdempotencia.guardar(cargaIdempotencia);

        ResumenCarga resumenCarga = puertoCargaPedidos.cargarPedidos(inputStream, tamanioLote);

        return ResponseEntity.status(HttpStatus.CREATED).body(resumenCarga);

    }
}
