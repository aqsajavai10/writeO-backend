package org.writeo.dao.mapper;

import org.writeo.dao.model.Notes;
import org.writeo.dao.dto.NotesDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotesMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public NotesDTO entityToDto(Notes entity) {
        return modelMapper.map(entity, NotesDTO.class);
    }

    public Notes dtoToEntity(NotesDTO dto) {
        return modelMapper.map(dto, Notes.class);
    }

    public Notes existingEntityToNewEntity(NotesDTO newDto, Notes existingEntity) {
        newDto.setNoteId(existingEntity.getNoteId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        if (newDto.getChapterId() == null) {
            newDto.setChapterId(existingEntity.getChapterId());
        }
        if (newDto.getData() == null) {
            newDto.setData(existingEntity.getData());
        }
        if (newDto.getType() == null) {
            newDto.setType(existingEntity.getType());
        }
        if (newDto.getCreationDate() == null) {
            newDto.setCreationDate(existingEntity.getCreationDate());
        }
        if (newDto.getAuthorId() == null) {
            newDto.setAuthorId(existingEntity.getAuthorId());
        }
        if (newDto.getLastUpdatedAt() == null) {
            newDto.setLastUpdatedAt(existingEntity.getLastUpdatedAt());
        }
        return modelMapper.map(newDto, Notes.class);
    }
}