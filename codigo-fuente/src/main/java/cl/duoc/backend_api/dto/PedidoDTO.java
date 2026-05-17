package cl.duoc.backend_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre; 
    private String direccionEnvio; // Atributo necesario añadido
    private List<ItemPedidoResponseDTO> productos; 
    private Double totalNeto;
    private Double descuento;
    private Double totalFinal;
    private String estado;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaLimiteEnvio;
}