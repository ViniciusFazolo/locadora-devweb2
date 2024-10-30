package com.vinicius.locadora.controller;

import com.vinicius.locadora.DTO.RequestDTO.DiretorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.DiretorResponseDTO;
import com.vinicius.locadora.service.DiretorService;
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

import java.util.List;

@RestController
@RequestMapping("/diretor")
public class DiretorController {
    @Autowired
    private DiretorService diretorService;

    @PostMapping("/novo")
    public ResponseEntity<DiretorResponseDTO> salvar(@RequestBody DiretorRequestDTO request){
        return diretorService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiretorResponseDTO> buscarPorId(@PathVariable int id){
        return diretorService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DiretorResponseDTO>> buscarTodos(){
        return diretorService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiretorResponseDTO> atualizar(@RequestBody DiretorRequestDTO request){
        return diretorService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return diretorService.deletar(id);
    }
}
