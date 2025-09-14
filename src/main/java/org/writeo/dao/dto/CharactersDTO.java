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
public class CharactersDTO {

    @PositiveOrZero(message = "Character ID must be zero or positive")
    @JsonProperty("character_id")
    private Long characterId;

    @NotNull(message = "Character name must not be null")
    @Size(max = 255, message = "Character name must not exceed 255 characters")
    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("character_description")
    private String characterDescription;

    @PositiveOrZero(message = "Mentions must be zero or positive")
    @JsonProperty("mentions")
    private Long mentions;

    @PositiveOrZero(message = "Mentions ID must be zero or positive")
    @JsonProperty("mentions_id")
    private Long mentionsId;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}