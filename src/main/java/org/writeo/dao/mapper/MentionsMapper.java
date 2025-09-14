package org.writeo.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.model.Mentions;
import org.writeo.dto.MentionsDTO;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MentionsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public MentionsDTO entityToDto(Mentions entity) {
        return modelMapper.map(entity, MentionsDTO.class);
    }

    public Mentions dtoToEntity(MentionsDTO dto) {
        return modelMapper.map(dto, Mentions.class);
    }


}
