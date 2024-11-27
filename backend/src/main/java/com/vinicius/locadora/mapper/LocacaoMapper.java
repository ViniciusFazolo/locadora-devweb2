package com.vinicius.locadora.mapper;

import org.mapstruct.Mapper;

import com.vinicius.locadora.DTO.RequestDTO.LocacaoRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.LocacaoResponseDTO;
import com.vinicius.locadora.model.Locacao;

@Mapper(componentModel = "spring")
public interface LocacaoMapper {
    
    LocacaoResponseDTO toDTO(Locacao locacao);
    Locacao toEntity(LocacaoRequestDTO locacaoRequestDTO);
}
