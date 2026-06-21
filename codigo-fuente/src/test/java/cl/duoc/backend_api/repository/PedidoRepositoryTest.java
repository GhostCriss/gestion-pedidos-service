package cl.duoc.backend_api.repository;

import cl.duoc.backend_api.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repository;

    @Test
    void debeGuardarPedido() {
        Pedido p = new Pedido();
        p.setClienteId(100L);
        p.setDireccionEnvio("Avenida Central 123");
        p.setEstado("PENDIENTE");
        p.setFechaPedido(LocalDateTime.now());
        
        Pedido guardado = repository.save(p);
        
        assertNotNull(guardado.getId());
        assertEquals(100L, guardado.getClienteId());
    }

    @Test
    void debeEncontrarPorId() {
        Pedido p = new Pedido();
        p.setClienteId(200L);
        p.setDireccionEnvio("Calle Principal 456");
        p.setEstado("EN PROCESO");
        p.setFechaPedido(LocalDateTime.now());
        Pedido guardado = repository.save(p);

        Optional<Pedido> encontrado = repository.findById(guardado.getId());
        
        assertTrue(encontrado.isPresent());
        assertEquals("EN PROCESO", encontrado.get().getEstado());
    }

    @Test
    void debeRetornarListaVaciaSiNoHayPedidos() {
        repository.deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }
}