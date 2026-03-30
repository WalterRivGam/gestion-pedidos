package tecnica.prueba.gestion_pedidos.infraestructura.adaptador.entrada;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada.PuertoCargaPedidos;
import tecnica.prueba.gestion_pedidos.aplicacion.servicio.ServicioCargaPedidos;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ErrorPedido;
import tecnica.prueba.gestion_pedidos.dominio.modelo.ResumenCarga;
import tecnica.prueba.gestion_pedidos.dominio.modelo.TipoError;
import tecnica.prueba.gestion_pedidos.infraestructura.adaptador.entrada.excepcion.ExcepcionErrorArchivo;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/pedidos")
public class ControladorPedido {

    @Value("${pedidos.tamanio.lote:500}")
    private int tamanioLote;

    private final PuertoCargaPedidos puertoCargaPedidos;

    public ControladorPedido(PuertoCargaPedidos puertoCargaPedidos) {
        this.puertoCargaPedidos = puertoCargaPedidos;
    }

    @PostMapping(value = "/cargar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumenCarga> cargarPedidos(@RequestHeader("Idempotency-Key") String idempotencyKey,
                                                      @RequestPart("file") MultipartFile file) {
        InputStream inputStream;
        String hash;
        try {
            inputStream = file.getInputStream();
            hash = DigestUtils.sha256Hex(inputStream);
        } catch (IOException e) {
            throw new ExcepcionErrorArchivo("Excepción I/O al leer el archivo o al obtener su hash");
        }

        ResumenCarga resumenCarga = puertoCargaPedidos.cargarPedidos(inputStream, idempotencyKey, hash, tamanioLote);

        return ResponseEntity.status(HttpStatus.CREATED).body(resumenCarga);

    }
}
