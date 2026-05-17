package cl.duoc.backend_api.service;

import cl.duoc.backend_api.client.AuthClient;
import cl.duoc.backend_api.dto.*;
import cl.duoc.backend_api.model.DetallePedido;
import cl.duoc.backend_api.model.Pedido;
import cl.duoc.backend_api.repository.PedidoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private AuthClient authClient; 

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirAEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO guardar(PedidoCreateDTO dto) {
        log.info("Iniciando creación de pedido para cliente ID: {}", dto.getClienteId());

        UsuarioDTO usuarioRemote;
        try {
            usuarioRemote = authClient.getUsuarioById(dto.getClienteId());
            if (!usuarioRemote.isActivo()) {
                throw new RuntimeException("El cliente no se encuentra activo en el sistema");
            }
        } catch (FeignException.NotFound e) {
            log.warn("Validación fallida: El cliente ID {} no existe en el servicio de Autenticación", dto.getClienteId());
            throw new RuntimeException("No se puede crear el pedido: Cliente no encontrado en el sistema");
        } catch (FeignException e) {
            log.error("Error de infraestructura de red al conectar con el servicio de Autenticación: {}", e.getMessage());
            throw new RuntimeException("Servicio de autenticación no disponible temporalmente");
        }

        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setDireccionEnvio(dto.getDireccionEnvio());
        pedido.setEstado("PENDIENTE");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaLimiteEnvio(LocalDateTime.now().plusDays(2)); 

        double acumuladorNeto = 0.0;

        for (ItemPedidoDTO itemDto : dto.getProductos()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProductoId(itemDto.getProductoId());
            detalle.setCantidad(itemDto.getCantidad());
            
            double precioHistoricoCopiado = 15000.0; 
            double subtotalItem = itemDto.getCantidad() * precioHistoricoCopiado;
            
            detalle.setPrecioUnitario(precioHistoricoCopiado);
            detalle.setSubtotal(subtotalItem);
            
            acumuladorNeto += subtotalItem;
            pedido.agregarDetalle(detalle);
        }

        pedido.setTotalNeto(acumuladorNeto);
        pedido.setDescuento(0.0); 
        pedido.setTotalFinal(acumuladorNeto - pedido.getDescuento());

        Pedido pedidoGuardado = repository.save(pedido);
        log.info("Pedido guardado exitosamente con ID interno MySQL: {}", pedidoGuardado.getId());

        PedidoDTO respuestaDto = convertirAEntityDto(pedidoGuardado);
        respuestaDto.setClienteNombre(usuarioRemote.getNombre());
        
        return respuestaDto;
    }

    private PedidoDTO convertirAEntityDto(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getClienteId());
        dto.setClienteNombre("Cliente ID: " + pedido.getClienteId()); 
        dto.setDireccionEnvio(pedido.getDireccionEnvio()); // Mapeo añadido
        dto.setTotalNeto(pedido.getTotalNeto());
        dto.setDescuento(pedido.getDescuento());
        dto.setTotalFinal(pedido.getTotalFinal());
        dto.setEstado(pedido.getEstado());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaLimiteEnvio(pedido.getFechaLimiteEnvio());

        List<ItemPedidoResponseDTO> itemDtos = pedido.getDetalles().stream().map(d -> {
            ItemPedidoResponseDTO itemDto = new ItemPedidoResponseDTO();
            itemDto.setProductoId(d.getProductoId());
            itemDto.setCodigoProducto("PROD-" + d.getProductoId());
            itemDto.setNombreProducto("Producto Genérico");
            itemDto.setCantidad(d.getCantidad());
            itemDto.setPrecioUnitario(d.getPrecioUnitario());
            itemDto.setSubtotal(d.getSubtotal());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setProductos(itemDtos);
        return dto;
    }

    public PedidoDTO obtenerPorId(Long id) {
        Pedido pedido = repository.findById(id) // Se corrige a 'repository'
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Pedido con ID " + id + " no encontrado"));
        
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getClienteId());
        dto.setDireccionEnvio(pedido.getDireccionEnvio()); // Ahora compilará correctamente
        dto.setEstado(pedido.getEstado());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaLimiteEnvio(pedido.getFechaLimiteEnvio());
        dto.setTotalNeto(pedido.getTotalNeto());
        dto.setDescuento(pedido.getDescuento());
        dto.setTotalFinal(pedido.getTotalFinal());
        
        return dto;
    }
}