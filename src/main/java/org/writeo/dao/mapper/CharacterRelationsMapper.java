package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.CharacterRelationsDTO;
import org.writeo.dao.model.CharacterRelations;

@Component
@AllArgsConstructor
public class CharacterRelationsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public CharacterRelationsDTO entityToDto(CharacterRelations entity) {
        return modelMapper.map(entity, CharacterRelationsDTO.class);
    }

    public CharacterRelations dtoToEntity(CharacterRelationsDTO dto) {
        return modelMapper.map(dto, CharacterRelations.class);
    }

    public CharacterRelations existingEntityToNewEntity(CharacterRelationsDTO newDto, CharacterRelations existingEntity) {
        newDto.setRelationId(existingEntity.getRelationId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, CharacterRelations.class);
    }
}