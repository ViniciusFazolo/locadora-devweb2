package com.vinicius.locadora.controller;

import java.util.List;

import com.vinicius.locadora.DTO.RequestDTO.ClasseRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ClasseResponseDTO;
import com.vinicius.locadora.service.ClasseService;
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

@RestController
@RequestMapping("/classe")
public class ClasseController {
      @Autowired
     private ClasseService classeService;

     @PostMapping("/novo")
     public ResponseEntity<ClasseResponseDTO> salvar(@RequestBody ClasseRequestDTO request){
         return classeService.salvar(request);
     }

     @GetMapping("/{id}")
     public ResponseEntity<ClasseResponseDTO> buscarPorId(@PathVariable int id){
         return classeService.buscarPorId(id);
     }

     @GetMapping("/listar")
     public ResponseEntity<List<ClasseResponseDTO>> buscarTodos(){
         return classeService.buscarTodos();
     }

     @PutMapping("/{id}")
     public ResponseEntity<ClasseResponseDTO> atualizar(@RequestBody ClasseRequestDTO request){
         return classeService.atualizar(request);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<String> deletar(@PathVariable int id){
         return classeService.deletar(id);
     }
}
