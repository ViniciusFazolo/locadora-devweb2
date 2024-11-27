package com.vinicius.locadora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.model.Socio;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.repository.SocioRepository;

@Service
public class SocioService{
    
    @Autowired
    private SocioRepository socioRepository;

    public ResponseEntity<Socio> salvar(Socio request) {
        Socio obj = new Socio();
        obj.setCpf(request.getCpf());
        obj.setDependente(request.getDependente());
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEndereco(request.getEndereco());
        obj.setEstahAtivo(request.getEstahAtivo());
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setNumInscricao(request.getNumInscricao());
        obj.setSexo(request.getSexo());
        obj.setTel(request.getTel());
        obj = socioRepository.save(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    public ResponseEntity<Socio> buscarPorId(int id){
        Socio obj = socioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + id));
        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<List<Socio>> buscarTodos(){
        List<Socio> obj = socioRepository.findAll();
        return ResponseEntity.ok().body(obj);
    }       

    public ResponseEntity<Socio> atualizar(Socio request){
        Socio obj = socioRepository.findById(request.getId()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id: " + request.getId()));
        obj.setCpf(request.getCpf());
        obj.setDependente(request.getDependente());
        obj.setDtNascimento(request.getDtNascimento());
        obj.setEndereco(request.getEndereco());
        obj.setEstahAtivo(request.getEstahAtivo());
        obj.setLocacao(request.getLocacao());
        obj.setNome(request.getNome());
        obj.setNumInscricao(request.getNumInscricao());
        obj.setSexo(request.getSexo());
        obj.setTel(request.getTel());
        obj = socioRepository.save(obj);
        
        socioRepository.save(obj);

        return ResponseEntity.ok().body(obj);
    }

    public ResponseEntity<String> deletar(int id){
        Socio obj = socioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o ator de id:" + id));
        socioRepository.delete(obj);
     
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }
}
