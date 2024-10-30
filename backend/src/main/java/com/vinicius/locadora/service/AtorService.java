package com.vinicius.locadora.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.DTO.RequestDTO.AtorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.AtorResponseDTO;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.mapper.AtorMapper;
import com.vinicius.locadora.model.Ator;
import com.vinicius.locadora.repository.AtorRepository;

@Service
public class AtorService{
    
    @Autowired
    private AtorRepository atorRepository;

    @Autowired
    private AtorMapper atorMapper;

    public ResponseEntity<AtorResponseDTO> salvar(AtorRequestDTO request) {
        if(request.nome() == null || request.nome().isBlank()){
            throw new PreencherTodosCamposException();
        }

        Ator obj = new Ator();
        obj.setNome(request.nome());
        obj.setTitulo(request.titulos());
        obj = atorRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(atorMapper.toDTO(obj));
    }

    public ResponseEntity<AtorResponseDTO> buscarPorId(int id){
        Ator obj = atorRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + id));
        return ResponseEntity.ok().body(atorMapper.toDTO(obj));
    }

    public ResponseEntity<List<AtorResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(atorRepository.findAll().stream().map(atorMapper::toDTO).collect(Collectors.toList()));
    }       

    public ResponseEntity<AtorResponseDTO> atualizar(AtorRequestDTO request){
        if(request.nome() == null || request.nome().isBlank()){
            throw new PreencherTodosCamposException();
        }

        Ator obj = atorRepository.findById(request.id()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + request.id()));
        
        obj.setNome(request.nome());
        obj.setTitulo(request.titulos());
        atorRepository.save(obj);

        return ResponseEntity.ok().body(atorMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Ator obj = atorRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id:" + id));
        atorRepository.delete(obj);
     
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }
}
