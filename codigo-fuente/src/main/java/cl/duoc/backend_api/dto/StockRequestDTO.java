package cl.duoc.backend_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class StockRequestDTO {
    private List<ItemDescuentoDTO> items;
}