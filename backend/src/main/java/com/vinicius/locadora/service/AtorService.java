package com.vinicius.locadora.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinicius.locadora.DTO.RequestDTO.AtorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.AtorResponseDTO;
import com.vinicius.locadora.mapper.AtorMapper;
import com.vinicius.locadora.model.Ator;
import com.vinicius.locadora.model.ResponseModel;
import com.vinicius.locadora.repository.AtorRepository;

@Service
public class AtorService{
    
    @Autowired
    private AtorRepository atorRepository;

    @Autowired
    private AtorMapper atorMapper;

    public ResponseModel<AtorResponseDTO> salvarAtor(AtorRequestDTO ator) {
        ResponseModel<AtorResponseDTO> response = new ResponseModel<>();
        try {
            response.setDados(atorMapper.toDTO(atorRepository.save(atorMapper.toEntity(ator))));
            response.setMensagem("Ator salvo com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao salvar ator");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<AtorResponseDTO> buscarPorId(int id){
        ResponseModel<AtorResponseDTO> response = new ResponseModel<>();
        try {
            response.setDados(atorMapper.toDTO(atorRepository.findById(id).orElseThrow()));
            response.setMensagem("Ator encontrado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar ator");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<List<AtorResponseDTO>> buscarTodos(){
        ResponseModel<List<AtorResponseDTO>> response = new ResponseModel<>();
        try {
            response.setDados(atorRepository.findAll().stream().map(atorMapper::toDTO).collect(Collectors.toList()));
            response.setMensagem("Atores encontrados com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar atores");
            response.setStatus(false);
        }
        return response;
    }       

    public ResponseModel<AtorResponseDTO> atualizar(int id, AtorRequestDTO request){
        ResponseModel<AtorResponseDTO> response = new ResponseModel<>();
        try {
            Ator ator = atorRepository.findById(id).orElseThrow();
            ator.setNome(request.nome());
            response.setDados(atorMapper.toDTO(atorRepository.save(ator)));
            response.setMensagem("Ator atualizado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao atualizar ator");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<String> deletar(int id){
        ResponseModel<String> response = new ResponseModel<>();
        try {
            atorRepository.deleteById(id);
            response.setMensagem("Ator deletado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao deletar ator");
            response.setStatus(false);
        }
        return response;
    }

}
