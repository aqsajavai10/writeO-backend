//package org.writeo.dao.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.writeo.annotation.Safe;
//import org.writeo.utils.consts.CommonConstants;
//import lombok.*;
//
//import jakarta.annotation.Nullable;
//import jakarta.persistence.Column;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.*;
//import java.util.Date;
//
//@Builder
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ModelFeaturesDTO {
//
//    @PositiveOrZero(message = CommonConstants.srNoPositiveMessage)
//    @JsonProperty("sr_no")
//    private Long srNo;
//
//    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
//    private String key;
//
//    @Positive(message = "model_code must be a positive value")
//    @JsonProperty("model_code")
//    private Long modelCode;
//
//
//    @Positive(message = "feature_id must be a positive value")
//    @JsonProperty("feature_id")
//    private Long featureId;
//
//    @NotNull(message = "is_active must not be null")
//    @Pattern(regexp = "[YN]", message = "is_active should be either 'Y' or 'N'")
//    //@Safe
//    @JsonProperty("is_active")
//    private String isActive;
//
//    @NotNull(message = "is_trainable_feature must not be null")
//    @Pattern(regexp = "[YN]", message = "is_trainable_feature should be either 'Y' or 'N'")
//    //@Safe
//    @JsonProperty("is_trainable_feature")
//    private String isTrainableFeature;
//
//    @Positive(message = "order_id must be a positive value")
//    @JsonProperty("order_id")
//    private Long orderId;
//
//    @JsonProperty("created_on")
//    private Date createdOn;
//
//    @JsonProperty("last_updated_at")
//    private Date lastUpdatedAt;
//
//    @Pattern(regexp = "[0-9]", message = "dbit_flag should be between 0-9")
//    //@Safe
//    @JsonProperty("dbit_flag")
//    private String dbitFlag;
//
//    @Positive(message = "rec_no must be a positive value")
//    @JsonProperty("rec_no")
//    private Long recNo;
//
//    @Nullable
//    @JsonProperty("feature_desc")
//    private String featureDesc;
//
//
//}