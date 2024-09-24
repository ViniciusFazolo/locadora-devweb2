package com.vinicius.locadora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.locadora.DTO.RequestDTO.AtorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.AtorResponseDTO;
import com.vinicius.locadora.model.ResponseModel;
import com.vinicius.locadora.service.AtorService;

@RestController
@RequestMapping("/ator")
public class AtorController {
    
    @Autowired
    private AtorService atorService;

    @PostMapping("/novo")
    public ResponseModel<AtorResponseDTO> salvar(@RequestBody AtorRequestDTO request){
        return atorService.salvarAtor(request);
    }

    @GetMapping("/{id}")
    public ResponseModel<AtorResponseDTO> buscarPorId(@PathVariable int id){
        return atorService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseModel<List<AtorResponseDTO>> buscarTodos(){
        return atorService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseModel<AtorResponseDTO> atualizar(@PathVariable int id, @RequestBody AtorRequestDTO request){
        return atorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseModel<String> deletar(@PathVariable int id){
        return atorService.deletar(id);
    }
}
