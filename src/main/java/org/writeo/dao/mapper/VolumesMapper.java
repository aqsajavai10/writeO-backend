package org.writeo.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.model.Volumes;
import org.writeo.dao.dto.VolumesDTO;
import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class VolumesMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public VolumesDTO entityToDto(Volumes entity) {
        return modelMapper.map(entity, VolumesDTO.class);
    }

    public Volumes dtoToEntity(VolumesDTO dto) {
        return modelMapper.map(dto, Volumes.class);
    }

    public Volumes existingEntityToNewEntity(VolumesDTO newDto, Volumes existingEntity) {
        newDto.setVolumeId(existingEntity.getVolumeId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        if (newDto.getNovelId() == null) {
            newDto.setNovelId(existingEntity.getNovelId());
        }
        if (newDto.getVolumeNumber() == null) {
            newDto.setVolumeNumber(existingEntity.getVolumeNumber());
        }
        if (newDto.getStartingChapter() == null) {
            newDto.setStartingChapter(existingEntity.getStartingChapter());
        }
        if (newDto.getEndingChapter() == null) {
            newDto.setEndingChapter(existingEntity.getEndingChapter());
        }
        if (newDto.getVolumeName() == null) {
            newDto.setVolumeName(existingEntity.getVolumeName());
        }
        return modelMapper.map(newDto, Volumes.class);
    }
}