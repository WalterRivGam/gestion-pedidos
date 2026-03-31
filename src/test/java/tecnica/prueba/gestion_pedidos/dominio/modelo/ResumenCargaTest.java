package tecnica.prueba.gestion_pedidos.dominio.modelo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ResumenCargaTest {
    @Test
    void dado_errores_cuando_agrupaErrores_entonces_erroresAgrupados() {
        ResumenCarga resumenCarga = new ResumenCarga();
        List<ErrorPedido> errores = new ArrayList<>();
        errores.add(new ErrorPedido(7, TipoError.FECHA_INVALIDA, "Fecha inválida"));
        errores.add(new ErrorPedido(5, TipoError.ESTADO_INVALIDO, "Estado inválido"));
        errores.add(new ErrorPedido(3, TipoError.FECHA_INVALIDA, "Fecha inválida"));
        errores.add(new ErrorPedido(8, TipoError.ESTADO_INVALIDO, "Estado inválido"));
        errores.add(new ErrorPedido(1, TipoError.FECHA_INVALIDA, "Fecha inválida"));
        resumenCarga.setErroresPedidos(errores);

        resumenCarga.agruparErrores();

        assertThat(resumenCarga.getErroresAgrupados())
                .contains(new ErrorAgrupado(TipoError.ESTADO_INVALIDO, 2, List.of(5, 8)),
                        new ErrorAgrupado(TipoError.FECHA_INVALIDA, 3, List.of(7, 3, 1)));

    }
}
