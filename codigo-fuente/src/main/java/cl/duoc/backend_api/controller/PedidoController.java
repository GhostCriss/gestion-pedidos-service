package cl.duoc.backend_api.controller;

import cl.duoc.backend_api.dto.PedidoCreateDTO;
import cl.duoc.backend_api.dto.PedidoDTO;
import cl.duoc.backend_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoCreateDTO dto) {
        PedidoDTO nuevoPedido = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
        PedidoDTO dto = service.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }
}