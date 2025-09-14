package org.writeo.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.validation.constraints.*;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotesDTO {

    @PositiveOrZero(message = CommonConstants.noteIdPositiveMessage)
    @JsonProperty("note_id")
    private Long noteId;

    @Positive(message = "Chapter ID must be a positive value")
    @JsonProperty("chapter_id")
    private Long chapterId;

    @JsonProperty("data")
    private String data;

    @NotNull(message = "Type must not be null")
    @Size(max = 255, message = "Type must not exceed 255 characters")
    @JsonProperty("type")
    private String type;

    @NotNull(message = "Creation date must not be null")
    @Size(max = 255, message = "Creation date must not exceed 255 characters")
    @JsonProperty("creation_date")
    private String creationDate;

    @NotNull(message = "Author ID must not be null")
    @Size(max = 255, message = "Author ID must not exceed 255 characters")
    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("last_updated_at")
    private Date lastUpdatedAt;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}