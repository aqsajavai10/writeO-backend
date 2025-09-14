//package org.writeo.dao.repository;
//
//import org.writeo.dao.model.ModelFeatures;
//import org.writeo.utils.consts.CommonConstants;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ModelFeaturesRepository extends JpaRepository<ModelFeatures,Long> {
//    @Query(value = CommonConstants.findAllModelFeaturesByModelCode,nativeQuery = true)
//    List<ModelFeatures> findAllByModelCode(Long model_code);
//
//
//    @Query(value =CommonConstants.findMaxModelFeatureId , nativeQuery = true)
//    Long findMaxModelFeatureId();
//    @Query(value = CommonConstants.findAllModelFeaturesByModelCodeAndFeatureId,nativeQuery = true)
//    ModelFeatures findAllByModelCodeAndFeatureId(Long model_code, Long feature_id);
//}
