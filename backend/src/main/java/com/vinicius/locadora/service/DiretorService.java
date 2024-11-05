package com.vinicius.locadora.service;

import com.vinicius.locadora.DTO.RequestDTO.DiretorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.DiretorResponseDTO;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.exceptions.RelacionamentoException;
import com.vinicius.locadora.mapper.DiretorMapper;
import com.vinicius.locadora.model.Diretor;
import com.vinicius.locadora.model.Titulo;
import com.vinicius.locadora.repository.DiretorRepository;
import com.vinicius.locadora.repository.TituloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private DiretorMapper diretorMapper;

    public ResponseEntity<DiretorResponseDTO> salvar(DiretorRequestDTO request) {
        if(request.nome() == null || request.nome().isBlank()){
            throw new PreencherTodosCamposException();
        }

        Diretor obj = new Diretor();
        obj.setNome(request.nome());
        obj.setTitulos(request.titulos());
        obj = diretorRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(diretorMapper.toDTO(obj));
    }

    public ResponseEntity<DiretorResponseDTO> buscarPorId(int id){
        Diretor obj = diretorRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o diretor de id: " + id));
        return ResponseEntity.ok().body(diretorMapper.toDTO(obj));
    }

    public ResponseEntity<List<DiretorResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(diretorRepository.findAll().stream().map(diretorMapper::toDTO).collect(Collectors.toList()));
    }

    public ResponseEntity<DiretorResponseDTO> atualizar(DiretorRequestDTO request){
        if(request.nome() == null || request.nome().isBlank()){
            throw new PreencherTodosCamposException();
        }

        Diretor obj = diretorRepository.findById(request.id()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o diretor de id: " + request.id()));
        
        obj.setNome(request.nome());
        obj.setTitulos(request.titulos());
        diretorRepository.save(obj);

        return ResponseEntity.ok().body(diretorMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Diretor obj = diretorRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o diretor de id:" + id));
        
        List<Titulo> titulos = tituloRepository.findAll();
        for(Titulo titulo : titulos){
            if(titulo.getDiretor().equals(obj)){
                throw new RelacionamentoException();
            }
        }

        diretorRepository.delete(obj);

        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }
}
