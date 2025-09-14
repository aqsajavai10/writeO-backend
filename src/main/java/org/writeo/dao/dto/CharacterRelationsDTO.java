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
public class CharacterRelationsDTO {

    @JsonProperty("relation_id")
    private Long relationId;

    @NotNull(message = "Character ID must not be null")
    @Positive(message = "Character ID must be positive")
    @JsonProperty("character_id")
    private Long characterId;

    @NotNull(message = "Relation to character ID must not be null")
    @Positive(message = "Relation to character ID must be positive")
    @JsonProperty("relation_to_character_id")
    private Long relationToCharacterId;

    @NotBlank(message = "Relation description must not be blank")
    @Size(max = 255, message = "Relation description must not exceed 255 characters")
    @JsonProperty("relation_description")
    private String relationDescription;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}