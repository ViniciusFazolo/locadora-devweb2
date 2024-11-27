package com.vinicius.locadora.controller;

import java.util.List;
import com.vinicius.locadora.model.Dependente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.locadora.service.DependenteService;

@RestController
@RequestMapping("/dependente")
public class DependenteController {
    
    @Autowired
    private DependenteService dependenteService;

    @PostMapping("/novo")
    public ResponseEntity<Dependente> salvar(@RequestBody Dependente request){
        return dependenteService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dependente> buscarPorId(@PathVariable int id){
        return dependenteService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Dependente>> buscarTodos(){
        return dependenteService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dependente> atualizar(@RequestBody Dependente request){
        return dependenteService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return dependenteService.deletar(id);
    }
}
