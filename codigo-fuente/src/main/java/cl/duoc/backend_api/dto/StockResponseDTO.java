package cl.duoc.backend_api.dto;

import lombok.Data;

@Data
public class StockResponseDTO {
    private boolean confirmado;
    private String mensaje;
}