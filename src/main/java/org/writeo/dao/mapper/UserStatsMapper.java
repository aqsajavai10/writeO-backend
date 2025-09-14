package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.UserStatsDTO;
import org.writeo.dao.model.UserStats;

@Component
@AllArgsConstructor
public class UserStatsMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public UserStatsDTO entityToDto(UserStats entity) {
        return modelMapper.map(entity, UserStatsDTO.class);
    }

    public UserStats dtoToEntity(UserStatsDTO dto) {
        return modelMapper.map(dto, UserStats.class);
    }

    public UserStats existingEntityToNewEntity(UserStatsDTO newDto, UserStats existingEntity) {
        newDto.setAuthorId(existingEntity.getAuthorId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, UserStats.class);
    }
}