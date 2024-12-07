package com.vinicius.locadora.controller;

import java.util.List;

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

import com.vinicius.locadora.DTO.RequestDTO.TituloRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.TituloResponseDTO;
import com.vinicius.locadora.model.Titulo;
import com.vinicius.locadora.service.TituloService;

@RestController
@RequestMapping("/titulo")
public class TituloController {
    
    @Autowired
    private TituloService tituloService;

    @PostMapping("/novo")
    public ResponseEntity<TituloResponseDTO> salvar(@RequestBody TituloRequestDTO request){
        return tituloService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TituloResponseDTO> buscarPorId(@PathVariable int id){
        return tituloService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TituloResponseDTO>> buscarTodos(){
        return tituloService.buscarTodos();
    }

    @PutMapping("/update")
    public ResponseEntity<TituloResponseDTO> atualizar(@RequestBody TituloRequestDTO request){
        return tituloService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return tituloService.deletar(id);
    }

    @GetMapping("/buscaPorNome/{nome}")
    public ResponseEntity<List<Titulo>> consultarPorNome(@PathVariable String nome) {
        return tituloService.consultarPorNome(nome);
    }

    @GetMapping("/buscaPorCategoria/{categoria}")
    public ResponseEntity<List<Titulo>> consultarPorCategoria(@PathVariable String categoria) {
        return tituloService.consultarPorCategoria(categoria);
    }

    @GetMapping("/buscaPorAtor/{ator}")
    public ResponseEntity<List<Titulo>> consultarPorAtor(@PathVariable String ator) {
        return tituloService.consultarPorAtor(ator);
    }
}
