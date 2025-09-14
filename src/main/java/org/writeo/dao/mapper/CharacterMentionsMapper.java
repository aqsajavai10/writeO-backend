package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.CharacterMentionsDTO;
import org.writeo.dao.model.CharacterMentions;

@Component
@AllArgsConstructor
public class CharacterMentionsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public CharacterMentionsDTO entityToDto(CharacterMentions entity) {
        return modelMapper.map(entity, CharacterMentionsDTO.class);
    }

    public CharacterMentions dtoToEntity(CharacterMentionsDTO dto) {
        return modelMapper.map(dto, CharacterMentions.class);
    }

    public CharacterMentions existingEntityToNewEntity(CharacterMentionsDTO newDto, CharacterMentions existingEntity) {
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, CharacterMentions.class);
    }
}