package com.vinicius.locadora.service;

import com.vinicius.locadora.DTO.RequestDTO.ClasseRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ClasseResponseDTO;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.exceptions.RelacionamentoException;
import com.vinicius.locadora.mapper.ClasseMapper;
import com.vinicius.locadora.model.Classe;
import com.vinicius.locadora.model.Titulo;
import com.vinicius.locadora.repository.ClasseRepository;
import com.vinicius.locadora.repository.TituloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private ClasseMapper classeMapper;

    public ResponseEntity<ClasseResponseDTO> salvar(ClasseRequestDTO request) {
        if(request.nome() == null || request.prazoDevolucao() == null || request.valor() == null){
            throw new PreencherTodosCamposException();
        }

        Classe obj = new Classe();
        obj.setNome(request.nome());
        obj.setValor(request.valor());
        obj.setPrazoDevolucao(request.prazoDevolucao());
        obj.setTitulos(request.titulos());
        obj = classeRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(classeMapper.toDTO(obj));
    }

    public ResponseEntity<ClasseResponseDTO> buscarPorId(int id){
        Classe obj = classeRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a classe de id: " + id));
        return ResponseEntity.ok().body(classeMapper.toDTO(obj));
    }

    public ResponseEntity<List<ClasseResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(classeRepository.findAll().stream().map(classeMapper::toDTO).collect(Collectors.toList()));
    }

    public ResponseEntity<ClasseResponseDTO> atualizar(ClasseRequestDTO request){
        if(request.nome() == null || request.nome().isBlank() || request.prazoDevolucao() == null || request.prazoDevolucao().equals("") || request.valor() == null){
            throw new PreencherTodosCamposException();
        }
        
        Classe obj = classeRepository.findById(request.id()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a classe de id: " + request.id()));
        
        obj.setNome(request.nome());
        obj.setValor(request.valor());
        obj.setPrazoDevolucao(request.prazoDevolucao());
        obj.setTitulos(request.titulos());
        classeRepository.save(obj);

        return ResponseEntity.ok().body(classeMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Classe obj = classeRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar a classe de id:" + id));
        
        List<Titulo> titulos = tituloRepository.findAll();
        for(Titulo titulo : titulos){
            if(titulo.getClasse().equals(obj)){
                throw new RelacionamentoException();
            }
        }

        classeRepository.delete(obj);

        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }
}
