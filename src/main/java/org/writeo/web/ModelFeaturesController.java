//package org.writeo.web;
//
//import org.writeo.service.ModelFeaturesService;
//import org.writeo.dao.compositekeys.ModelFeatureId;
//import org.writeo.dao.dto.*;
//import org.writeo.service.*;
//import org.writeo.utils.consts.CommonConstants;
//import org.writeo.utils.exceps.CustomNullPointerException;
//import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Positive;
//import java.util.*;
//
//@RestController
//
//@RequestMapping(value = "/modelfeatures")
//@Log4j2
//@Getter
//@Setter
//@AllArgsConstructor
//public class ModelFeaturesController {
//
//    @Autowired
//    private final ModelFeaturesService modelFeaturesService;
//
//
//
//
//    public ResponseEntity<AiAdminResponse> findAllAttributes(Long featureID) {
//        List<FeatureOperandsDTO> featureOperands = featureOperandsService.findAllByFeatureId(featureID);
//
//        if (featureOperands.isEmpty()) {
//            return ResponseHandlerUtil.handleSuccess(featureOperands);
//        } else {
//            try {
//                List<FeatureOperandsDTO> finalOperandsSet = new ArrayList<>();
//
//
//                for (FeatureOperandsDTO features : featureOperands) {
//                    if (CommonConstants.featureOperandTypeA.equals(features.getOperandType())) {
//                        finalOperandsSet.add(features);
//                    } else if (CommonConstants.featureOperandTypeF.equals(features.getOperandType())) {
//                        List<FeatureOperandsDTO> subList = (List<FeatureOperandsDTO>) findAllAttributes(features.getOperandRefId()).getBody().getResponsePayload();
//                        finalOperandsSet.addAll(subList);
//                    }
//                }
//                return ResponseHandlerUtil.handleSuccess(finalOperandsSet);
//            } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                     DataAccessException e) {
//               log.warn(log.isWarnEnabled() ? "Failed to fetch model Attributes: {}" : null);
//                throw e;
//            }
//        }
//    }
//
//    @GetMapping(value = "/get", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> getModelFeatures() {
//        try {
//            log.info(log.isInfoEnabled() ? "GET request received to fetch model features." : null);
//            List<ModelFeaturesDTO> modelFeatures = modelFeaturesService.findAll();
//            log.info(log.isInfoEnabled() ? "Model features fetched successfully." : null);
//            return ResponseHandlerUtil.handleSuccess(modelFeatures);
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to fetch model features: {}" : null);
//            throw e;
//        }
//    }
//
//    @GetMapping(value = "/getByModelCode/{modelCode}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> getModelFeatures(@PathVariable Long modelCode) {
//        try {
//            log.info(log.isInfoEnabled() ? "GET request received to fetch model features." : null);
//            List<ModelFeaturesDTO> modelFeatures = modelFeaturesService.findAllByModelCode(modelCode);
//            for (ModelFeaturesDTO modelFeature : modelFeatures) {
//                modelFeature.setFeatureDesc(
//                        featureService.findById(modelFeature.getFeatureId()).getFeatureDesc()
//                );
//            }
//            log.info(log.isInfoEnabled() ? "Model features fetched successfully." : null);
//            return ResponseHandlerUtil.handleSuccess(modelFeatures);
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to fetch model features: {}" : null);
//            throw e;
//        }
//    }
//
//
//    public List<TrainDatasetDetailsDTO> addFeatureOpearand(@Positive(message = "dataset_id must be a positive value") Long dataset_id, Set<FeatureOperandsDTO> featureOperandsList) {
//        try {
//            List<TrainDatasetDetailsDTO> trainDatasetDetails = new ArrayList<>();
//
//            for (FeatureOperandsDTO dataAttributes : featureOperandsList) {
//                TrainDatasetDetailsDTO trainDatasetDetail = new TrainDatasetDetailsDTO();
//                trainDatasetDetail.setAttrId(dataAttributes.getOperandRefId());
//                trainDatasetDetail.setDatasetId(dataset_id);
//                if (dataAttributes.getOperandRefId() != null && dataAttributes.getOperandRefId() == CommonConstants.transactionId.longValue()) {
//                    trainDatasetDetail.setAttrType(CommonConstants.keyAttribute);
//                } else if (dataAttributes.getOperandRefId() != null && dataAttributes.getOperandRefId() == CommonConstants.labelColumn.longValue()) {
//                    trainDatasetDetail.setAttrType(CommonConstants.labelAttribute);
//                }
//                trainDatasetDetail.setDatasetId(dataset_id);
//                trainDatasetDetail.setIsActive("Y");
//
//
//                trainDatasetDetails.add(trainDatasetDetail);
//            }
//            List<TrainDatasetDetailsDTO> trainDatasetDetailsInserted = trainDatasetDetailsService.insertByDatasetAllOperands(trainDatasetDetails);
//            return trainDatasetDetailsInserted;
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException ex) {
//            log.error("Failed to insert model attributes: {}", ex.getMessage());
//            throw ex;
//        }
//
//
//    }
//
//    @PostMapping(value = "/add", produces = "application/json")
//    @Transactional(noRollbackFor = { CustomNullPointerException.class, CustomRecordAlreadyExistsException.class, DataAccessException.class })
//    public ResponseEntity<AiAdminResponse> insertModelFeatures(@Valid @RequestBody List<ModelFeaturesDTO> featuresList) {
//        try {
//
//            List<ModelFeaturesDTO> featuresInserted = new ArrayList<>();
//            Set<FeatureOperandsDTO> featureOperandsList = new HashSet<>();
//
//            featuresList.forEach(features -> {
//                featureOperandsList.addAll((Collection<? extends FeatureOperandsDTO>) Objects.requireNonNull(findAllAttributes(features.getFeatureId()).getBody()).getResponsePayload());
//                featuresInserted.add(modelFeaturesService.insertByModel(features));
//            });
//
//
//            List<TrainDatasetGroupDetailsDTO> trainDatasetGroupDetailsDTOS = trainDatasetGroupDetailsService.findByAllByDatasetGrpId(model1sService.findById(featuresList.get(0).getModelCode()).getDataId());
//            Set<TrainDatasetDetailsDTO> trainDatasetDetailsInserted=new HashSet<>() ;
//            trainDatasetGroupDetailsDTOS.stream().forEach(trainDatasetGroupDetailsDTO -> {
//
//                trainDatasetDetailsInserted.addAll(  addFeatureOpearand(trainDatasetGroupDetailsDTO.getDatasetId(), featureOperandsList));
//            });
//
//            return ResponseHandlerUtil.handleSuccess(trainDatasetDetailsInserted);
//        } catch ( CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to insert model features: {}" : null);
//            throw e;
//        }
//    }
//
//    @GetMapping(value = "/modelAttributes/getByModelCode/{modelCode}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> getAttributesByModelCode(@PathVariable Long modelCode) {
//        try {
//            log.info(log.isInfoEnabled() ? "GET request received to fetch attributes by model code." : null);
//
//            List<ModelFeaturesDTO> modelFeatures = modelFeaturesService.findAllByModelCode(modelCode);
//
//            if (modelFeatures.isEmpty()) {
//                return ResponseHandlerUtil.handleSuccess(modelFeatures);
//            }
//
//            Set<FeatureOperandsDTO> featureOperandsList = new HashSet<>();
//
//            modelFeatures.forEach(features -> {
//                featureOperandsList.addAll((List<FeatureOperandsDTO>) findAllAttributes(features.getFeatureId()).getBody().getResponsePayload());
//            });
//
//            if (!featureOperandsList.isEmpty()) {
//                List<Long> attrIds = new ArrayList<>();
//
//                featureOperandsList.forEach(fe -> {
//                    attrIds.add(fe.getFeatureId());
//                });
//
//                List<DataAttributesDTO> dataAttributes = dataAttributesService.findInList(attrIds);
//                return ResponseHandlerUtil.handleSuccess(dataAttributes);
//            }
//
//            return ResponseHandlerUtil.handleSuccess("No Attributes by such model Code");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to fetch attributes by model code: {}" : null);
//            throw e;
//        }
//    }
//
//    @PostMapping(value = "/updateByModelFeatureById", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> updateModelFeatures(@Valid @RequestBody ModelFeaturesDTO modelFeatures) {
//        try {
//            log.info(log.isInfoEnabled() ? "PUT request received to update model features." : null);
//            ModelFeaturesDTO updatedFeatures = modelFeaturesService.update(modelFeatures);
//            log.info(log.isInfoEnabled() ? "Model features updated successfully." : null);
//            return ResponseHandlerUtil.handleSuccess(updatedFeatures);
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to update model features: {}" : null);
//            throw e;
//        }
//    }
//
//
//    @PostMapping(value = "/deleteByModelCode/{modelCode}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> deleteModelFeatures(@PathVariable Long modelCode) {
//        try {
//            log.info(log.isInfoEnabled() ? "DELETE request received to delete model features." : null);
//            modelFeaturesService.deleteByModelCode(modelCode);
//            log.info(log.isInfoEnabled() ? "Model features deleted successfully." : null);
//            return ResponseHandlerUtil.handleSuccess("Model features deleted successfully.");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to delete model features: " : null);
//            throw e;
//        }
//    }
//
//    @PostMapping(value = "/deleteByModelCodeIdInBatch", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> deleteModelCodeInBatch(@RequestBody List<Long> modelCode) {
//        try {
//            log.info(log.isInfoEnabled() ? "DELETE request received to delete model features." : null);
//            for (Long id : modelCode) {
//                modelFeaturesService.deleteByModelCode(id);
//            }
//            log.info(log.isInfoEnabled() ? "Model features deleted successfully." : null);
//            return ResponseHandlerUtil.handleSuccess("Model features deleted successfully.");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to delete model features: {}" : null);
//            throw e;
//        }
//    }
//
//    @PostMapping(value = "/deleteByModelCodeAndFeatureId/{modelCode}/{featureId}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> deleteModelFeatures(@PathVariable Long modelCode, @PathVariable Long featureId) {
//        try {
//            log.info(log.isInfoEnabled() ? "DELETE request received to delete model features." : null);
//            modelFeaturesService.delete(new ModelFeatureId(modelCode, featureId));
//            log.info(log.isInfoEnabled() ? "Model features deleted successfully." : null);
//            return ResponseHandlerUtil.handleSuccess("Model features deleted successfully.");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to delete model features: {}" : null);
//            throw e;
//        }
//
//
//    }
//
//    @PostMapping(value = "/deleteByModelCodeAndFeatureIdInBatch/{modelCode}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> deleteModelFeaturesInBatch(@PathVariable Long modelCode, @RequestBody List<Long> featureIds) {
//        try {
//            log.info(log.isInfoEnabled() ? "DELETE request received to delete model features." : null);
//
//            featureIds.stream().forEach(id -> {
//                modelFeaturesService.delete(new ModelFeatureId(modelCode, id));
//            });
//
//            log.info(log.isInfoEnabled() ? "Model features deleted successfully." : null);
//            return ResponseHandlerUtil.handleSuccess("Model features deleted successfully.");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to delete model features: {}" : null);
//            throw e;
//        }
//    }
//
//    @PostMapping(value = "/unlinkByModelCodeAndFeatureIdInBatch/{modelCode}", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> unlinkModelFeaturesInBatch(@PathVariable Long modelCode, @RequestBody List<Long> featureIds) {
//        try {
//            log.info(log.isInfoEnabled() ? "Unlink request received to delete model features." : null);
//
//            featureIds.stream().forEach(id -> {
//                ModelFeaturesDTO featuresDTO= new ModelFeaturesDTO();
//                featuresDTO.setIsActive("N");
//                featuresDTO.setModelCode(modelCode);
//                featuresDTO.setFeatureId(id);
//                modelFeaturesService.update(featuresDTO);
//            });
//
//            log.info(log.isInfoEnabled() ? "Model features deleted successfully." : null);
//            return ResponseHandlerUtil.handleSuccess("Model features deleted successfully.");
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//            log.warn(log.isWarnEnabled() ? "Failed to delete model features: {}" : null);
//            throw e;
//        }
//    }
//
//    @PostMapping(value = "/addFeature", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<AiAdminResponse> addModelFeature(@Valid @RequestBody ModelFeaturesDTO modelFeature) {
//        try {
//            log.info(log.isInfoEnabled() ? "POST request received to add a model feature." : null);
//
//
//            ModelFeaturesDTO addedFeature = modelFeaturesService.insertByModel(modelFeature);
//            log.info(log.isInfoEnabled() ? "Model feature added successfully." : null);
//            return ResponseHandlerUtil.handleSuccess(addedFeature);
//        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | ArrayIndexOutOfBoundsException |
//                 DataAccessException e) {
//           log.warn(log.isWarnEnabled() ? "Failed to add model feature: {}" : null);
//            throw e;
//        }
//    }
//}
