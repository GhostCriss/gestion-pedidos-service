package cl.duoc.backend_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponseDTO {

    private Long productoId;
    private String codigoProducto; // Identificador único visible (ej: PROD-001) [cite: 3047, 3189]
    private String nombreProducto; // Nombre descriptivo del catálogo [cite: 3190]
    private Integer cantidad; // Unidades adquiridas [cite: 3191]
    private Double precioUnitario; // Precio histórico congelado al momento de la venta [cite: 3013, 3192]
    private Double subtotal; // Cálculo automático de cantidad multiplicada por el precio unitario [cite: 3009, 3194]
}