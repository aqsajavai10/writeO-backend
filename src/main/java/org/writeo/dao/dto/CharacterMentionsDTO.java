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
public class CharacterMentionsDTO {

    @Positive(message = "Character ID must be a positive value")
    @JsonProperty("character_id")
    private Long characterId;

    @Positive(message = "Mention ID must be a positive value")
    @JsonProperty("mention_id")
    private Long mentionId;

    @Positive(message = "Chapter ID must be a positive value")
    @JsonProperty("chapter_id")
    private Long chapterId;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}