package cl.duoc.backend_api.client;


import cl.duoc.backend_api.dto.StockRequestDTO;
import cl.duoc.backend_api.dto.StockResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Apuntamos directo al puerto 8087 de Inventario
@FeignClient(name = "inventario-service", url = "http://localhost:8087")
public interface InventarioClient {

    @PostMapping("/api/inventario/descontar")
    StockResponseDTO descontar(@RequestBody StockRequestDTO request);
}