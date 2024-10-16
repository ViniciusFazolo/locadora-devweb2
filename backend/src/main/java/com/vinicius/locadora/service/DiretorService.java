package com.vinicius.locadora.service;

import com.vinicius.locadora.DTO.RequestDTO.DiretorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.DiretorResponseDTO;
import com.vinicius.locadora.mapper.DiretorMapper;
import com.vinicius.locadora.model.Diretor;
import com.vinicius.locadora.model.ResponseModel;
import com.vinicius.locadora.repository.DiretorRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    @Autowired
    private DiretorMapper diretorMapper;

    public ResponseModel<DiretorResponseDTO> salvar(DiretorRequestDTO obj) {
        ResponseModel<DiretorResponseDTO> response = new ResponseModel<>();

        if(obj.nome() == null){
            response.setMensagem("Preencha todos os campos");
            response.setStatus(false);
            return response;
        }

        try {
            response.setDados(diretorMapper.toDTO(diretorRepository.save(diretorMapper.toEntity(obj))));
            response.setMensagem("Registro salvo com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao salvar registro");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<DiretorResponseDTO> buscarPorId(int id){
        ResponseModel<DiretorResponseDTO> response = new ResponseModel<>();
        try {
            response.setDados(diretorMapper.toDTO(diretorRepository.findById(id).orElseThrow()));
            response.setMensagem("Registro encontrado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar registro");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<List<DiretorResponseDTO>> buscarTodos(){
        ResponseModel<List<DiretorResponseDTO>> response = new ResponseModel<>();
        try {
            response.setDados(diretorRepository.findAll().stream().map(diretorMapper::toDTO).collect(Collectors.toList()));
            response.setMensagem("Registros encontrados com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar registros");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<DiretorResponseDTO> atualizar(int id, DiretorRequestDTO request){
        ResponseModel<DiretorResponseDTO> response = new ResponseModel<>();

        if(request.nome() == null){
            response.setMensagem("Preencha todos os campos");
            response.setStatus(false);
            return response;
        }

        try {
            Diretor obj = diretorRepository.findById(id).orElseThrow();
            obj.setNome(request.nome());

            response.setDados(diretorMapper.toDTO(diretorRepository.save(obj)));
            response.setMensagem("Registro atualizado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao atualizar registro");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<String> deletar(int id){
        ResponseModel<String> response = new ResponseModel<>();
        try {
            diretorRepository.deleteById(id);
            response.setMensagem("Registro deletado com sucesso");
        } catch (ConstraintViolationException e){
            response.setMensagem("Não foi possível apagar o registro, pois, está vinculado a títulos");
            response.setStatus(false);
        }
        return response;
    }
}
