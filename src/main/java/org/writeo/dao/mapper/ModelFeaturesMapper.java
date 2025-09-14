//package org.writeo.dao.mapper;
//
//
//
//import org.writeo.dao.model.ModelFeatures;
//import org.writeo.dao.dto.ModelFeaturesDTO;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@AllArgsConstructor
//public class ModelFeaturesMapper {
//    @Autowired
//    private final ModelMapper modelMapper;
//
//
//    public ModelFeaturesDTO entityToDto(ModelFeatures entity) {
//        return modelMapper.map(entity, ModelFeaturesDTO.class);
//    }
//
//    public ModelFeatures dtoToEntity(ModelFeaturesDTO dto) {
//        return modelMapper.map(dto, ModelFeatures.class);
//    }
//    public ModelFeatures existingEntityToNewEntity(ModelFeaturesDTO newDto, ModelFeatures existingEntity) {
//
//        newDto.setSrNo(existingEntity.getSrNo());
//        newDto.setCreatedOn(existingEntity.getCreatedOn());
//        newDto.setLastUpdatedAt(new Date());
//        if (newDto.getKey() == null) {
//            newDto.setKey(existingEntity.getKey());
//        }
//
//        if (newDto.getIsActive() == null) {
//            newDto.setIsActive(existingEntity.getIsActive());
//        }
//        if (newDto.getDbitFlag() == null) {
//            newDto.setDbitFlag(existingEntity.getDbitFlag());
//        }
//        if (newDto.getRecNo() == null) {
//            newDto.setRecNo(existingEntity.getRecNo());
//        }
//        if (newDto.getOrderId() == null) {
//            newDto.setOrderId(existingEntity.getOrderId());
//        }
//        if (newDto.getIsTrainableFeature() == null) {
//            newDto.setIsTrainableFeature(existingEntity.getIsTrainableFeature());
//        }
//        return modelMapper.map(newDto, ModelFeatures.class);
//    }
//}
