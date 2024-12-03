package com.vinicius.locadora.controller;

import java.util.List;
import com.vinicius.locadora.model.Socio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.locadora.service.SocioService;

@RestController
@RequestMapping("/socio")
public class SocioController {
    
    @Autowired
    private SocioService socioService;

    @PostMapping("/novo")
    public ResponseEntity<Socio> salvar(@RequestBody Socio request){
        return socioService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socio> buscarPorId(@PathVariable int id){
        return socioService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Socio>> buscarTodos(){
        return socioService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Socio> atualizar(@RequestBody Socio request){
        return socioService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return socioService.deletar(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable int id) {
        return socioService.alterarStatus(id);
    }
}
