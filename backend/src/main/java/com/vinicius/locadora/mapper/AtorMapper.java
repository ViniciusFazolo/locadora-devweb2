package com.vinicius.locadora.mapper;

import org.mapstruct.Mapper;

import com.vinicius.locadora.DTO.RequestDTO.AtorRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.AtorResponseDTO;
import com.vinicius.locadora.model.Ator;

@Mapper(componentModel = "spring")
public interface AtorMapper {
    
    AtorResponseDTO toDTO(Ator ator);
    Ator toEntity(AtorRequestDTO atorRequestDTO);
}
