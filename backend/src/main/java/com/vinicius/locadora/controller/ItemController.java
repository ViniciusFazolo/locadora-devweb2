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

import com.vinicius.locadora.DTO.RequestDTO.ItemRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ItemResponseDTO;
import com.vinicius.locadora.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @PostMapping("/novo")
    public ResponseEntity<ItemResponseDTO> salvar(@RequestBody ItemRequestDTO request){
        return itemService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> buscarPorId(@PathVariable int id){
        return itemService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ItemResponseDTO>> buscarTodos(){
        return itemService.buscarTodos();
    }

    @PutMapping("/update")
    public ResponseEntity<ItemResponseDTO> atualizar(@RequestBody ItemRequestDTO request){
        return itemService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return itemService.deletar(id);
    }
}
