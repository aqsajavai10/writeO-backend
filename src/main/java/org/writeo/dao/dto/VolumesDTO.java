package org.writeo.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.constraints.*;
import org.writeo.utils.CommonConstants.CommonConstants;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumesDTO {

    @PositiveOrZero(message = CommonConstants.volumeIdPositiveMessage)
    @JsonProperty("volume_id")
    private Long volumeId;

    @Positive(message = "Novel ID must be a positive value")
    @JsonProperty("novel_id")
    private Long novelId;

    @Positive(message = "Volume number must be a positive value")
    @JsonProperty("volume_number")
    private Double volumeNumber;

    @PositiveOrZero(message = "Starting chapter must be zero or positive")
    @JsonProperty("starting_chapter")
    private Double startingChapter;

    @PositiveOrZero(message = "Ending chapter must be zero or positive")
    @JsonProperty("ending_chapter")
    private Double endingChapter;

    @NotNull(message = "Volume name must not be null")
    @Size(max = 255, message = "Volume name must not exceed 255 characters")
    @JsonProperty("volume_name")
    private String volumeName;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}