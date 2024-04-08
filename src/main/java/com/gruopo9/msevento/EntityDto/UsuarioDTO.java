package com.gruopo9.msevento.EntityDto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioDTO {

    private Long idUsuario;

    private String username;

    private String password;

    private String email;

    private String telefono;

    private boolean enabled;

    private boolean accountnonexpire;

    private boolean accountnonlocked;

    private boolean credentialsnonexpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<RolDTO> roles = new HashSet<>();
}