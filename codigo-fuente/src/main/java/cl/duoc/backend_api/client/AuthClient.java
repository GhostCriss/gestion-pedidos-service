package cl.duoc.backend_api.client;

import cl.duoc.backend_api.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "auth-client", 
    url = "${auth.service.url}" // La URL se inyecta dinámicamente desde el application.properties
)
public interface AuthClient {

    @GetMapping("/auth/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);
}