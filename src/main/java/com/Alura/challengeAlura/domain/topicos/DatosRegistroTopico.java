package com.Alura.challengeAlura.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull LocalDate fecha,
        @NotNull Status status,
        @NotBlank String autor,
        @NotNull Curso curso
) {
}
