package tecnica.prueba.gestion_pedidos.infraestructura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.entrada.PuertoCargaPedidos;
import tecnica.prueba.gestion_pedidos.aplicacion.puerto.salida.*;
import tecnica.prueba.gestion_pedidos.aplicacion.servicio.ServicioCargaPedidos;

@Configuration
public class ConfiguracionBean {
    @Bean
    public PuertoCargaPedidos puertoCargaPedidos(PuertoIdempotencia puertoIdempotencia,
                                                 PuertoArchivo puertoArchivo,
                                                 PuertoCliente puertoCliente,
                                                 PuertoZona puertoZona,
                                                 PuertoPedido puertoPedido) {
        return new ServicioCargaPedidos(puertoIdempotencia, puertoArchivo, puertoCliente,
                puertoZona, puertoPedido);
    }
}
