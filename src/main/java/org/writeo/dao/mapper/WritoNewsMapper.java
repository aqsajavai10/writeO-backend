package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.WritoNewsDTO;
import org.writeo.dao.model.WritoNews;

@Component
@AllArgsConstructor
public class WritoNewsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public WritoNewsDTO entityToDto(WritoNews entity) {
        return modelMapper.map(entity, WritoNewsDTO.class);
    }

    public WritoNews dtoToEntity(WritoNewsDTO dto) {
        return modelMapper.map(dto, WritoNews.class);
    }

    public WritoNews existingEntityToNewEntity(WritoNewsDTO newDto, WritoNews existingEntity) {
        newDto.setWritonewsId(existingEntity.getWritonewsId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, WritoNews.class);
    }
}