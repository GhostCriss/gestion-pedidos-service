package cl.duoc.backend_api.service;

import cl.duoc.backend_api.dto.PedidoDTO;
import cl.duoc.backend_api.model.Pedido;
import cl.duoc.backend_api.repository.PedidoRepository;
import cl.duoc.backend_api.client.AuthClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private AuthClient authClient;

    @InjectMocks
    private PedidoService service;

    @Test
    void debeListarTodosLosPedidos() {
        Pedido p = new Pedido();
        p.setId(1L);
        p.setClienteId(10L);
        p.setEstado("PENDIENTE");
        
        when(repository.findAll()).thenReturn(Arrays.asList(p));

        List<PedidoDTO> resultado = service.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
    }

    @Test
    void debeObtenerPedidoPorIdExitosamente() {
        Pedido p = new Pedido();
        p.setId(2L);
        p.setClienteId(20L);
        
        when(repository.findById(2L)).thenReturn(Optional.of(p));

        PedidoDTO resultado = service.obtenerPorId(2L);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
    }

    @Test
    void debeLanzarExcepcionCuandoPedidoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Validamos la regla de negocio: Si el ID no existe, lanza EntityNotFoundException
        EntityNotFoundException excepcion = assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerPorId(99L);
        });

        assertTrue(excepcion.getMessage().contains("no encontrado"));
    }
}