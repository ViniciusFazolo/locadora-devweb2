package com.vinicius.locadora.mapper;

import org.mapstruct.Mapper;

import com.vinicius.locadora.DTO.RequestDTO.TituloRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.TituloResponseDTO;
import com.vinicius.locadora.model.Item;
import com.vinicius.locadora.model.Titulo;

@Mapper(componentModel = "spring")
public interface TituloMapper {
    
    TituloResponseDTO toDTO(Titulo titulo);
    Item toEntity(TituloRequestDTO tituloRequestDTO);
}
