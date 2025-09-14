package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.NovelsDTO;
import org.writeo.dao.model.Novels;

import java.util.Date;

@Component
@AllArgsConstructor
public class NovelsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public NovelsDTO entityToDto(Novels entity) {
        return modelMapper.map(entity, NovelsDTO.class);
    }

    public Novels dtoToEntity(NovelsDTO dto) {
        return modelMapper.map(dto, Novels.class);
    }

    public Novels existingEntityToNewEntity(NovelsDTO newDto, Novels existingEntity) {
        newDto.setNovelId(existingEntity.getNovelId());
        newDto.setStartDate(existingEntity.getStartDate());
        newDto.setEndDate(new Date());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        if (newDto.getCompletionStatus() == null) {
            newDto.setCompletionStatus(existingEntity.getCompletionStatus().toString());
        }
        if (newDto.getTotalVolumes() == null) {
            newDto.setTotalVolumes(existingEntity.getTotalVolumes());
        }
        return modelMapper.map(newDto, Novels.class);
    }
}