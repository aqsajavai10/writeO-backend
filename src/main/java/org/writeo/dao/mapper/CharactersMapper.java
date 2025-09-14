package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.CharactersDTO;
import org.writeo.dao.model.Characters;

@Component
@AllArgsConstructor
public class CharactersMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public CharactersDTO entityToDto(Characters entity) {
        return modelMapper.map(entity, CharactersDTO.class);
    }

    public Characters dtoToEntity(CharactersDTO dto) {
        return modelMapper.map(dto, Characters.class);
    }

    public Characters existingEntityToNewEntity(CharactersDTO newDto, Characters existingEntity) {
        newDto.setCharacterId(existingEntity.getCharacterId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        if (newDto.getMentions() == null) {
            newDto.setMentions(existingEntity.getMentions());
        }
        if (newDto.getMentionsId() == null) {
            newDto.setMentionsId(existingEntity.getMentionsId());
        }
        return modelMapper.map(newDto, Characters.class);
    }
}