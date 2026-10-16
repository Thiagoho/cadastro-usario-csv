package com.projeto.cadastro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.projeto.cadastro.entities.Usuario;
import com.projeto.cadastro.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return service.cadastrar(usuario);
    }
}