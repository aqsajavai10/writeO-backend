package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.UserAuthDTO;
import org.writeo.dao.model.UserAuth;

import java.util.Date;

@Component
@AllArgsConstructor
public class UserAuthMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public UserAuthDTO entityToDto(UserAuth entity) {
        return modelMapper.map(entity, UserAuthDTO.class);
    }

    public UserAuth dtoToEntity(UserAuthDTO dto) {
        return modelMapper.map(dto, UserAuth.class);
    }

    public UserAuth existingEntityToNewEntity(UserAuthDTO newDto, UserAuth existingEntity) {
        newDto.setUserId(existingEntity.getUserId());
        if (newDto.getLastLogin() == null) {
            newDto.setLastLogin(existingEntity.getLastLogin());
        }
        if (newDto.getLastLoginDevice() == null) {
            newDto.setLastLoginDevice(existingEntity.getLastLoginDevice());
        }
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, UserAuth.class);
    }
}