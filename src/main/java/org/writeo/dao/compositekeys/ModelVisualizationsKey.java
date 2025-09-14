//package org.writeo.dao.compositekeys;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.*;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Id;
//import java.io.Serializable;
//
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//public class ModelVisualizationsKey implements Serializable{
//    private static final long serialVersionUID = 1L;
//    @Id
//    @Column(name  ="model_code")
//    @JsonProperty("model_code")
//    private Long modelCode;
//
//    @Id
//    @Column(name  ="visualization_id")
//    @JsonProperty("visualization_id")
//    private Long visualizationId;
//}