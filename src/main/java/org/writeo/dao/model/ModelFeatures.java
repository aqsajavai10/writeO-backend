//package org.writeo.dao.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//import org.writeo.dao.compositekeys.ModelFeatureId;
//import org.writeo.utils.consts.CommonConstants;
//import lombok.*;
//import org.springframework.lang.Nullable;
//import java.util.Date;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Table(name = CommonConstants.modelFeatures)
//@IdClass(ModelFeatureId.class)
//public class ModelFeatures {
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "sr_no")
//    private Long srNo;
//
//    @Transient
//    private String key;
//
//    @Id
//    @Column(name = "model_code")
//    private Long modelCode;
//
//    @Column(name = "feature_id")
//    @Id
//    private Long featureId;
//
//    @Nullable
//    @Column(name = "is_active")
//    private String isActive;
//
//    @Nullable
//    @Column(name = "dbit_flag")
//    private String dbitFlag;
//
//    @Nullable
//    @Column(name = "rec_no")
//    private Long recNo;
//
//    @Column(name = "last_updated_at")
//    private Date lastUpdatedAt;
//
//    @Column(name = "is_trainable_feature")
//    private String isTrainableFeature;
//
//    @Column(name = "order_id")
//    private Long orderId;
//
//    @Nullable
//    @Column(name = "created_on")
//    private Date createdOn;
//
//    @Nullable
//    @Transient
//    private String featureDesc;
//
//    @PostUpdate @PostLoad
//    public void populateKey() {
//        this.key =CommonConstants.modelFeatures.concat("_").concat(String.valueOf(this.modelCode)).concat("|").concat(CommonConstants.features).concat("_").concat(String.valueOf(this.featureId));
//    }
//}
