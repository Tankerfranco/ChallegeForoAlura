package com.Alura.challengeAlura.controllers;

import com.Alura.challengeAlura.domain.usuarios.DatosAutentificacionUsuario;
import com.Alura.challengeAlura.domain.usuarios.DatosJWT;
import com.Alura.challengeAlura.domain.usuarios.Usuario;
import com.Alura.challengeAlura.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")

public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "obtiene el token para el usuario")
    public ResponseEntity autentificar(@RequestBody @Valid DatosAutentificacionUsuario datos) {
        Authentication tokenAuth = new UsernamePasswordAuthenticationToken(datos.login(),datos.clave());
        var usuarioAutenticado = authenticationManager.authenticate(tokenAuth);
        var tokenJWT = tokenService.generateToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWT(tokenJWT));
    }

}
