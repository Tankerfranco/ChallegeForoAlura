package com.Alura.challengeAlura.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosAutentificacionUsuario(
       @NotBlank String login,
       @NotBlank String clave
) {
}
