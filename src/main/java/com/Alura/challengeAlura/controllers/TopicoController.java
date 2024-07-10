package com.Alura.challengeAlura.controllers;

import com.Alura.challengeAlura.domain.topicos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private ITopicoRepository repo;

    //Creacion
    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo topico")
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody DatosRegistroTopico datos,
                                                            UriComponentsBuilder uriComponentsBuilder){
        var topico = repo.save(new Topico(datos));
        var datosRespuesta = new DatosRespuestaTopico(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFecha(),
                topico.getStatus(),topico.getAutor(),topico.getCurso());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }
    //Traer Todos
    @GetMapping
    @Operation(summary = "Trae todos los topicos de la base de datos")
    public ResponseEntity<Page<DatosRespuestaTopico>> obtenerTopicos(@PageableDefault(size = 2) Pageable pageable){
        return ResponseEntity.ok(repo.findAll(pageable).map(DatosRespuestaTopico::new));
    }
    //Traer Uno en especifico
    @GetMapping("/{id}")
    @Operation(summary = "Trae un topico especifico de la base de datos")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id){
        Topico topico = repo.getReferenceById(id);
        var registroTopico = new DatosRespuestaTopico(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFecha(),
                topico.getStatus(),topico.getAutor(),topico.getCurso());
        return ResponseEntity.ok(registroTopico);
    }
    //Eliminar
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un topico a eleccion")
    public ResponseEntity borrarTopico(@PathVariable Long id){
        Topico topico = repo.getReferenceById(id);
        repo.delete(topico);
        return ResponseEntity.noContent().build();
    }
    //Actualizar
    @PutMapping
    @Transactional
    @Operation(summary = "Modifica un topico a eleccion")
    public ResponseEntity modificarTopico(@RequestBody @Valid DatosActualizarTopico datos){
        var topico = repo.getReferenceById(datos.id());
        topico.actualizarTopico(datos);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFecha()
                ,topico.getStatus(),topico.getAutor(),topico.getCurso()));
    }


}
