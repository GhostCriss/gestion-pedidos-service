package cl.duoc.backend_api.controller;

import cl.duoc.backend_api.dto.PedidoDTO;
import cl.duoc.backend_api.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService service;

    @InjectMocks
    private PedidoController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void debeRetornarListaDePedidosYStatus200() throws Exception {
        PedidoDTO p = new PedidoDTO();
        p.setId(1L);
        when(service.listarTodos()).thenReturn(Arrays.asList(p));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk());
    }

    @Test
    void debeRetornarPedidoPorIdYStatus200() throws Exception {
        PedidoDTO p = new PedidoDTO();
        p.setId(1L);
        when(service.obtenerPorId(1L)).thenReturn(p);

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void debeRetornar404SiRutaEsInvalida() throws Exception {
        mockMvc.perform(get("/api/rutafalsa"))
                .andExpect(status().isNotFound());
    }
}