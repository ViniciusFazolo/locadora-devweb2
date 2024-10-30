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
import com.vinicius.locadora.service.TituloService;

@RestController
@RequestMapping("/titulo")
public class TituloController {
    
    @Autowired
    private TituloService itemService;

    @PostMapping("/novo")
    public ResponseEntity<TituloResponseDTO> salvar(@RequestBody TituloRequestDTO request){
        return itemService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TituloResponseDTO> buscarPorId(@PathVariable int id){
        return itemService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TituloResponseDTO>> buscarTodos(){
        return itemService.buscarTodos();
    }

    @PutMapping("/update")
    public ResponseEntity<TituloResponseDTO> atualizar(@RequestBody TituloRequestDTO request){
        return itemService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return itemService.deletar(id);
    }
}
