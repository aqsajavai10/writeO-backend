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
public class ChaptersDTO {

    @PositiveOrZero(message = CommonConstants.chapterIdPositiveMessage)
    @JsonProperty("ch_id")
    private Long chId;

    @Positive(message = "Novel ID must be a positive value")
    @JsonProperty("novel_id")
    private Long novelId;

    @NotNull(message = "Chapter name must not be null")
    @Size(max = 255, message = "Chapter name must not exceed 255 characters")
    @JsonProperty("chapter_name")
    private String chapterName;

    @JsonProperty("content")
    private String content;

    @PositiveOrZero(message = "Word count must be zero or positive")
    @JsonProperty("word_count")
    private Integer wordCount;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}