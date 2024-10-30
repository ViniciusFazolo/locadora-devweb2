package com.vinicius.locadora.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.DTO.RequestDTO.ItemRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ItemResponseDTO;
import com.vinicius.locadora.exceptions.ItemNaoEncontradoException;
import com.vinicius.locadora.exceptions.PreencherTodosCamposException;
import com.vinicius.locadora.mapper.ItemMapper;
import com.vinicius.locadora.model.Item;
import com.vinicius.locadora.repository.ItemRepository;

@Service
public class ItemService{
    
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ResponseEntity<ItemResponseDTO> salvar(ItemRequestDTO request) {
        if(request.numSerie() == 0 || request.dtAquisicao().equals("") || request.dtAquisicao() == null || request.tipoItem().isBlank() || request.tipoItem() == null){
            throw new PreencherTodosCamposException();
        }
        
        Item obj = new Item();
        obj.setNumSerie(request.numSerie());
        obj.setDtAquisicao(request.dtAquisicao());
        obj.setTipoItem(request.tipoItem());
        obj.setTitulo(request.titulo());
        obj = itemRepository.save(obj);
      
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMapper.toDTO(obj));
    }

    public ResponseEntity<ItemResponseDTO> buscarPorId(int id){
        Item obj = itemRepository.findById(id).orElseThrow(() -> new ItemNaoEncontradoException(id));
        return ResponseEntity.ok().body(itemMapper.toDTO(obj));
    }

    public ResponseEntity<List<ItemResponseDTO>> buscarTodos(){
        return ResponseEntity.ok().body(itemRepository.findAll().stream().map(itemMapper::toDTO).collect(Collectors.toList()));
    }       

    public ResponseEntity<ItemResponseDTO> atualizar(ItemRequestDTO request){
        if(request.numSerie() == 0 || request.dtAquisicao().equals("") || request.      dtAquisicao() == null || request.tipoItem().isBlank() || request.tipoItem() == null){
            throw new PreencherTodosCamposException();
        }
        
        Item obj = itemRepository.findById(request.id()).orElseThrow(() -> new ItemNaoEncontradoException(request.id()));

        obj.setNumSerie(request.numSerie());
        obj.setDtAquisicao(request.dtAquisicao());
        obj.setTipoItem(request.tipoItem());
        obj.setTitulo(request.titulo());
        obj = itemRepository.save(obj);

        return ResponseEntity.ok().body(itemMapper.toDTO(obj));
    }

    public ResponseEntity<String> deletar(int id){
        Item obj = itemRepository.findById(id).orElseThrow(() -> new ItemNaoEncontradoException(id));
        itemRepository.delete(obj);
     
        return ResponseEntity.ok().body("Registro excluído com sucesso");
    }
}
