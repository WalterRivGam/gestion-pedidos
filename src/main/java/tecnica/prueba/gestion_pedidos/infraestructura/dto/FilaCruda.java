package tecnica.prueba.gestion_pedidos.infraestructura.dto;

import lombok.Data;

@Data
public class FilaCruda {
    private String numeroPedido;
    private String clienteId;
    private String fechaEntrega;
    private String estado;
    private String zonaEntrega;
    private String requiereRefrigeracion;
}
