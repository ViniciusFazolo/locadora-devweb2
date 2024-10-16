package com.vinicius.locadora.service;

import com.vinicius.locadora.DTO.RequestDTO.ClasseRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ClasseResponseDTO;
import com.vinicius.locadora.mapper.ClasseMapper;
import com.vinicius.locadora.model.Classe;
import com.vinicius.locadora.model.ResponseModel;
import com.vinicius.locadora.repository.ClasseRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ClasseMapper classeMapper;

    public ResponseModel<ClasseResponseDTO> salvar(ClasseRequestDTO obj) {
        ResponseModel<ClasseResponseDTO> response = new ResponseModel<>();

        if(obj.nome() == null || obj.prazoDevolucao() == null || obj.valor() == null){
            response.setMensagem("Preencha todos os campos");
            response.setStatus(false);
            return response;
        }

        try {
            response.setDados(classeMapper.toDTO(classeRepository.save(classeMapper.toEntity(obj))));
            response.setMensagem("Registro salvo com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao salvar registro");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<ClasseResponseDTO> buscarPorId(int id){
        ResponseModel<ClasseResponseDTO> response = new ResponseModel<>();
        try {
            response.setDados(classeMapper.toDTO(classeRepository.findById(id).orElseThrow()));
            response.setMensagem("Registro encontrado com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar registro");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<List<ClasseResponseDTO>> buscarTodos(){
        ResponseModel<List<ClasseResponseDTO>> response = new ResponseModel<>();
        try {
            response.setDados(classeRepository.findAll().stream().map(classeMapper::toDTO).collect(Collectors.toList()));
            response.setMensagem("Registros encontrados com sucesso");
        } catch (Exception e) {
            response.setMensagem("Erro ao buscar registros");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseModel<ClasseResponseDTO> atualizar(int id, ClasseRequestDTO request){
        ResponseModel<ClasseResponseDTO> response = new ResponseModel<>();

        if(request.nome() == null || request.prazoDevolucao() == null || request.valor() == null){
            response.setMensagem("Preencha todos os campos");
            response.setStatus(false);
            return response;
        }
        
        try {
            Classe obj = classeRepository.findById(id).orElseThrow();
            obj.setNome(request.nome());
            obj.setValor(request.valor());
            obj.setPrazoDevolucao(request.prazoDevolucao());

            response.setDados(classeMapper.toDTO(classeRepository.save(obj)));
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
            classeRepository.deleteById(id);
            response.setMensagem("Registro deletado com sucesso");
        } catch (ConstraintViolationException e){
            response.setMensagem("Erro ao deletar registro. Ele está vínculado a outros registros");
            response.setStatus(false);
        }
        return response;
    }
}
