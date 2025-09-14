package org.writeo.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.validation.constraints.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicsDTO {

    @Positive(message = "Character ID must be a positive value")
    @JsonProperty("character_id")
    private Long characterId;

    @Size(max = 255, message = "Objective must not exceed 255 characters")
    @JsonProperty("objective")
    private String objective;

    @PositiveOrZero(message = "Level must be zero or positive")
    @JsonProperty("level")
    private Integer level;

    @PositiveOrZero(message = "Age must be zero or positive")
    @JsonProperty("age")
    private Integer age;

    @Size(max = 255, message = "Race must not exceed 255 characters")
    @JsonProperty("race")
    private String race;

    @PositiveOrZero(message = "Energy must be zero or positive")
    @JsonProperty("energy")
    private Integer energy;

    @Size(max = 255, message = "Type must not exceed 255 characters")
    @JsonProperty("type")
    private String type;

    @Size(max = 255, message = "Reputation must not exceed 255 characters")
    @JsonProperty("reputation")
    private String reputation;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}