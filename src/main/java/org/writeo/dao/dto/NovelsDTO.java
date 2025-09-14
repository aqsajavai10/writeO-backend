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
public class NovelsDTO {

    @PositiveOrZero(message = CommonConstants.novelIdPositiveMessage)
    @JsonProperty("novel_id")
    private Long novelId;

    @NotNull(message = "Novel name must not be null")
    @Size(max = 255, message = "Novel name must not exceed 255 characters")
    @JsonProperty("novel_name")
    private String novelName;

    @JsonProperty("novel_description")
    private String novelDescription;

    @PositiveOrZero(message = "Total volumes must be zero or positive")
    @JsonProperty("total_volumes")
    private Double totalVolumes;

    @JsonProperty("title_pic")
    private String titlePic;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("end_date")
    private Date endDate;

    @Pattern(regexp = "[YN]", message = "Completion status should be either 'Y' or 'N'")
    @JsonProperty("completion_status")
    private String completionStatus;

    @Positive(message = "Author ID must be a positive value")
    @JsonProperty("author_id")
    private Long authorId;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}