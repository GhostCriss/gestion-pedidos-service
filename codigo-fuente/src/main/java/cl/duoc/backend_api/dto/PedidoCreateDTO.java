package cl.duoc.backend_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String direccionEnvio;

    @NotEmpty(message = "El pedido debe incluir al menos un producto")
    private List<ItemPedidoDTO> productos;

    // Opcional por si Diego o Josefa mandan un cupón
    private String codigoPromocion; 
}