package cl.duoc.backend_api.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void debeAsignarYRecuperarValores() {
        Pedido p = new Pedido();
        p.setClienteId(1L);
        p.setEstado("PENDIENTE");
        
        assertEquals(1L, p.getClienteId());
        assertEquals("PENDIENTE", p.getEstado());
    }

    @Test
    void debeMantenerSincronizacionBidireccionalAlAgregarDetalle() {
        Pedido pedido = new Pedido();
        DetallePedido detalle = new DetallePedido();
        detalle.setProductoId(10L);

        // Act: Usamos el método helper de tu clase Pedido
        pedido.agregarDetalle(detalle);

        // Assert: El detalle se agrega a la lista y su atributo 'pedido' apunta al padre
        assertEquals(1, pedido.getDetalles().size());
        assertEquals(pedido, detalle.getPedido());
    }

    @Test
    void debeInicializarListaDeDetallesVacia() {
        Pedido pedido = new Pedido();
        
        assertNotNull(pedido.getDetalles());
        assertTrue(pedido.getDetalles().isEmpty());
    }
}