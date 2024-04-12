package com.gruopo9.msevento.client;
import com.gruopo9.msevento.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-security")
public interface UsuarioClient {
    @GetMapping("/api/v1/autenticacion/usuarioautenticado")
    Usuario getUsuarioAutenticado(@RequestHeader("Authorization") String token);
}
