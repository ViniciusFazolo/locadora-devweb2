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

import com.vinicius.locadora.DTO.RequestDTO.LocacaoRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.LocacaoResponseDTO;
import com.vinicius.locadora.service.LocacaoService;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {
    
    @Autowired
    private LocacaoService locacaoService;

    @PostMapping("/novo")
    public ResponseEntity<LocacaoResponseDTO> salvar(@RequestBody LocacaoRequestDTO request){
        return locacaoService.salvar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocacaoResponseDTO> buscarPorId(@PathVariable int id){
        return locacaoService.buscarPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LocacaoResponseDTO>> buscarTodos(){
        return locacaoService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocacaoResponseDTO> atualizar(@RequestBody LocacaoRequestDTO request){
        return locacaoService.atualizar(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        return locacaoService.deletar(id);
    }

    @GetMapping("/cancelar")
    public ResponseEntity<String> cancelar(@PathVariable int id){
        return locacaoService.cancelar(id);
    }
    
    @GetMapping("/devolucao/{numSerie}")
    public ResponseEntity<String> devolucao(@PathVariable int numSerie){
        return locacaoService.devolucao(numSerie);
    }
}
