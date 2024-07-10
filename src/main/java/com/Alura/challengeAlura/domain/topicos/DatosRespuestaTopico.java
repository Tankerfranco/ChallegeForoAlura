package com.Alura.challengeAlura.domain.topicos;

import java.time.LocalDate;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fecha,
        Status status,
        String autor,
        Curso curso
) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(),topico.getMensaje(),topico.getFecha(),
                topico.getStatus(),topico.getAutor(),topico.getCurso());
    }
}
