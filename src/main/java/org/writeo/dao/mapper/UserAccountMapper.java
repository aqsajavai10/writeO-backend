package org.writeo.dao.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.writeo.dao.dto.UserAccountDTO;
import org.writeo.dao.model.UserAccount;

@Component
@AllArgsConstructor
public class UserAccountMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public UserAccountDTO entityToDto(UserAccount entity) {
        return modelMapper.map(entity, UserAccountDTO.class);
    }

    public UserAccount dtoToEntity(UserAccountDTO dto) {
        return modelMapper.map(dto, UserAccount.class);
    }

    public UserAccount existingEntityToNewEntity(UserAccountDTO newDto, UserAccount existingEntity) {
        newDto.setUserId(existingEntity.getUserId());
        if (newDto.getKey() == null) {
            newDto.setKey(existingEntity.getKey());
        }
        return modelMapper.map(newDto, UserAccount.class);
    }
}