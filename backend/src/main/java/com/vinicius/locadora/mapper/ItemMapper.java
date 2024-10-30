package com.vinicius.locadora.mapper;

import org.mapstruct.Mapper;

import com.vinicius.locadora.DTO.RequestDTO.ItemRequestDTO;
import com.vinicius.locadora.DTO.ResponseDTO.ItemResponseDTO;
import com.vinicius.locadora.model.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    
    ItemResponseDTO toDTO(Item ator);
    Item toEntity(ItemRequestDTO itemRequestDTO);
}
