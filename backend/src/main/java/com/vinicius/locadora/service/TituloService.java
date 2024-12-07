package com.vinicius.locadora.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.vinicius.locadora.DTO.RequestDTO.TituloRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.TituloResponseDTO;
import com.vinicius.locadora.exceptions.ObjetoNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.exceptions.RelacionamentoException;
import com.vinicius.locadora.mapper.TituloMapper;
import com.vinicius.locadora.model.Item;
import com.vinicius.locadora.model.Titulo;
import com.vinicius.locadora.repository.ItemRepository;
import com.vinicius.locadora.repository.TituloRepository;

@Service
public class TituloService{
    
    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TituloMapper tituloMapper;

    public ResponseEntity<TituloResponseDTO> salvar(TituloRequestDTO request) {
        if(request.ano().equals(null) || request.ano() == null || request.diretor() == null || request.diretor() == null || request.classe() == null || request.ator() == null || request.nome().isBlank() || request.nome() == null || request.sinopse().isBlank() || request.sinopse() == null || request.categoria() == null || request.categoria().isBlank()){
            throw new PreencherTodosCamposException();
        }
        
        Titulo obj = new Titulo();
        obj.setAno(request.ano());
        obj.setNome(request.nome());
        obj.setSinopse(request.sinopse());
        obj.setCategoria(request.categoria());
        obj.setAtor(request.ator());
        obj.setClasse(request.classe());
        obj.setDiretor(request.diretor());
        obj.setItems(request.items());
        obj = tituloRepository.save(obj);
      
        return ResponseEntity.status(HttpStatus.CREATED).body(tituloMapper.toDTO(obj));
    }

    public ResponseEntity<TituloResponseDTO> buscarPorId(int id){
        Titulo obj = tituloRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o titulo de id: " + id));
        return ResponseEntity.ok().body(tituloMapper.toDTO(obj));
    }

    public ResponseEntity<List<TituloResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(tituloRepository.findAll().stream().map(tituloMapper::toDTO).collect(Collectors.toList()));
    }       

    public ResponseEntity<TituloResponseDTO> atualizar(TituloRequestDTO request){
        if(request.ano().equals(null) || request.ano() == null || request.diretor() == null || request.diretor() == null || request.classe() == null || request.ator() == null || request.nome().isBlank() || request.nome() == null || request.sinopse().isBlank() || request.sinopse() == null || request.categoria() == null || request.categoria().isBlank()){
            throw new PreencherTodosCamposException();
        }
        
        Titulo obj = tituloRepository.findById(request.id()).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o titulo de id: " + request.id()));

        obj.setAno(request.ano());
        obj.setNome(request.nome());
        obj.setSinopse(request.sinopse());
        obj.setCategoria(request.categoria());
        obj.setAtor(request.ator());
        obj.setClasse(request.classe());
        obj.setDiretor(request.diretor());
        obj.setItems(request.items());
        obj = tituloRepository.save(obj);

        return ResponseEntity.ok().body(tituloMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Titulo obj = tituloRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível encontrar o titulo de id: " + id));
        
        List<Item> itens = itemRepository.findAll();
        for(Item item : itens){
            if(item.getTitulo().equals(obj)){
                throw new RelacionamentoException();
            }
        }

        tituloRepository.delete(obj);
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }

    public ResponseEntity<List<Titulo>> consultarPorNome(String nome){
        return ResponseEntity.ok().body(tituloRepository.findByNome(nome));
    }

    public ResponseEntity<List<Titulo>> consultarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok().body(tituloRepository.findByCategoria(categoria));
    }

    public ResponseEntity<List<Titulo>> consultarPorAtor(@PathVariable String ator) {
        return ResponseEntity.ok().body(tituloRepository.findByAtor(ator));
    }
}
