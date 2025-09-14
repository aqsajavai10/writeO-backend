//package org.writeo.service.impl;
//
//import org.writeo.dao.compositekeys.ModelFeatureId;
//import org.writeo.dao.dto.ModelFeaturesDTO;
//import org.writeo.dao.mapper.ModelFeaturesMapper;
//import org.writeo.dao.model.ModelFeatures;
//import org.writeo.dao.repository.ModelFeaturesRepository;
//import org.writeo.service.ModelFeaturesService;
//import org.writeo.utils.exceps.CustomInvalidFormatException;
//import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;
//import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Log4j2
//@AllArgsConstructor
//public class ModelFeaturesServiceImpl implements ModelFeaturesService {
//
//    @Autowired
//    private final ModelFeaturesRepository modelFeaturesRepository;
//    @Autowired
//    private final ModelFeaturesMapper modelFeaturesMapper;
//
//    @Override
//    public List<ModelFeaturesDTO> findAll() {
//        log.info(log.isInfoEnabled() ? "Fetching all ModelFeatures" : " ");
//        List<ModelFeatures> modelFeaturesList = modelFeaturesRepository.findAll();
//        return modelFeaturesList.stream()
//                .map(modelFeaturesMapper::entityToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ModelFeaturesDTO> findAllByModelCode(Long modelCode) {
//        log.info(log.isInfoEnabled() ? "Fetching ModelFeatures by model code: " + modelCode : " ");
//        List<ModelFeatures> modelFeaturesList = modelFeaturesRepository.findAllByModelCode(modelCode);
//        return modelFeaturesList.stream()
//                .map(modelFeaturesMapper::entityToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @SneakyThrows
//    public ModelFeaturesDTO insertByModel(ModelFeaturesDTO modelFeaturesDTO) {
//        log.info(log.isInfoEnabled() ? "Inserting ModelFeatures: " + modelFeaturesDTO : " ");
//
//        if (modelFeaturesDTO.getFeatureId() == null || modelFeaturesDTO.getModelCode() == null) {
//            //throw new CustomInvalidFormatException(CommonConstants.modelFeatures);
//        }
//
//
//        ModelFeatures featuresDTO= modelFeaturesRepository.findAllByModelCodeAndFeatureId(modelFeaturesDTO.getModelCode(), modelFeaturesDTO.getFeatureId());
//        if(featuresDTO!=null){
//            //throw new CustomRecordAlreadyExistsException("Model Feature already associated");
//        }
//        modelFeaturesDTO.setCreatedOn(new Date());
//        modelFeaturesDTO.setLastUpdatedAt(new Date());
//        ModelFeatures modelFeaturesEntity = modelFeaturesMapper.dtoToEntity(modelFeaturesDTO);
//        modelFeaturesRepository.save(modelFeaturesEntity);
//        return modelFeaturesMapper.entityToDto(modelFeaturesEntity);
//    }
//
//    @Override
//    @SneakyThrows
//    public ModelFeaturesDTO update(ModelFeaturesDTO modelFeaturesDTO) {
//        log.info(log.isInfoEnabled() ? "Updating ModelFeatures: " + modelFeaturesDTO : " ");
//
//        if (modelFeaturesDTO.getFeatureId() == null || modelFeaturesDTO.getModelCode() == null) {
//           // throw new CustomInvalidFormatException(CommonConstants.modelFeatures);
//        }
//
//
//        ModelFeaturesDTO existingModelFeature = this.findByModelCodeAndFeatureId(modelFeaturesDTO.getModelCode(), modelFeaturesDTO.getFeatureId());
//
//        ModelFeatures modelFeaturesEntity = modelFeaturesMapper.existingEntityToNewEntity(modelFeaturesDTO, modelFeaturesMapper.dtoToEntity(existingModelFeature));
//
//
//        return modelFeaturesMapper.entityToDto( modelFeaturesRepository.save(modelFeaturesEntity));
//    }
//
//    @Override
//    @SneakyThrows
//    public void delete(ModelFeatureId id) {
//        log.info(log.isInfoEnabled() ? "Deleting ModelFeatures with id: " + id : " ");
//
//        ModelFeatures modelFeaturesEntity = modelFeaturesMapper.dtoToEntity(this.findByModelCodeAndFeatureId(id.getModelCode(), id.getFeatureId()));
//
//        modelFeaturesRepository.delete(modelFeaturesEntity);
//        modelFeaturesMapper.entityToDto(modelFeaturesEntity);
//    }
//
//    @Override
//    public void deleteByModelCode(Long id) {
//        List<ModelFeaturesDTO> modelFeaturesList = this.findAllByModelCode(id);
//
//        modelFeaturesRepository.deleteAllInBatch(modelFeaturesList.stream().map(modelFeaturesMapper::dtoToEntity).collect(Collectors.toList()));
//    }
//
//    @Override
//    public ModelFeaturesDTO findById(Long id) {
//        return null;
//    }
//
//    //@Override
////    @SneakyThrows
////    public ModelFeaturesDTO findById(Long id) {
////        log.info(log.isInfoEnabled() ? "Fetching ModelFeatures by id: " + id : " ");
////
////     ModelFeatures modelFeaturesEntity = modelFeaturesRepository.findById(id)
////               .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.modelFeatures));
////
////        return modelFeaturesMapper.entityToDto(modelFeaturesEntity);
////    }
//
//    @Override
//    @SneakyThrows
//    public ModelFeaturesDTO findByModelCodeAndFeatureId(Long modelCode, Long featureId) {
//        log.info(log.isInfoEnabled() ? "Fetching ModelFeatures by id: {}" + modelCode : " ");
//
//        ModelFeatures features = modelFeaturesRepository.findAllByModelCodeAndFeatureId(modelCode, featureId);
//        if (features == null) {
//           // throw new CustomNoSuchRecordExistsException(CommonConstants.modelFeatures);
//        }
//        return modelFeaturesMapper.entityToDto(features);
//    }
//}
