package com.vinicius.locadora.mapper;

import com.vinicius.locadora.DTO.RequestDTO.DiretorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.DiretorResponseDTO;
import com.vinicius.locadora.model.Diretor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiretorMapper {
    DiretorResponseDTO toDTO(Diretor obj);
    Diretor toEntity(DiretorRequestDTO requestDTO);
}
