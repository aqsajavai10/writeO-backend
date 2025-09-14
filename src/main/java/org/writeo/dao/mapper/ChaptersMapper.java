package org.writeo.dao.mapper;

import org.writeo.dao.model.Chapters;
import org.writeo.dao.dto.ChaptersDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChaptersMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public ChaptersDTO entityToDto(Chapters entity) {
        return modelMapper.map(entity, ChaptersDTO.class);
    }

    public Chapters dtoToEntity(ChaptersDTO dto) {
        return modelMapper.map(dto, Chapters.class);
    }

    public Chapters existingEntityToNewEntity(ChaptersDTO newDto, Chapters existingEntity) {
        newDto.setChId(existingEntity.getChId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        if (newDto.getNovelId() == null) {
            newDto.setNovelId(existingEntity.getNovelId());
        }
        if (newDto.getChapterName() == null) {
            newDto.setChapterName(existingEntity.getChapterName());
        }
        if (newDto.getContent() == null) {
            newDto.setContent(existingEntity.getContent());
        }
        if (newDto.getWordCount() == null) {
            newDto.setWordCount(existingEntity.getWordCount());
        }
        return modelMapper.map(newDto, Chapters.class);
    }
}