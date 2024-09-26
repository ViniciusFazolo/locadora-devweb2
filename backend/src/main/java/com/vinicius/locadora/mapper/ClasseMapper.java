package com.vinicius.locadora.mapper;

import com.vinicius.locadora.DTO.RequestDTO.ClasseRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ClasseResponseDTO;
import com.vinicius.locadora.model.Classe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClasseMapper {
    ClasseResponseDTO toDTO(Classe obj);
    Classe toEntity(ClasseRequestDTO requestDTO);
}
