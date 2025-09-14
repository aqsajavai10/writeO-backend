package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.CharacteristicsDTO;
import org.writeo.dao.model.Characteristics;

@Component
@AllArgsConstructor
public class CharacteristicsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public CharacteristicsDTO entityToDto(Characteristics entity) {
        return modelMapper.map(entity, CharacteristicsDTO.class);
    }

    public Characteristics dtoToEntity(CharacteristicsDTO dto) {
        return modelMapper.map(dto, Characteristics.class);
    }

    public Characteristics existingEntityToNewEntity(CharacteristicsDTO newDto, Characteristics existingEntity) {
        newDto.setCharacterId(existingEntity.getCharacterId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, Characteristics.class);
    }
}