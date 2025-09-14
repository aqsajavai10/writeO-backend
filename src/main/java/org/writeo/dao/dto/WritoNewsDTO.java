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
public class WritoNewsDTO {

    @PositiveOrZero(message = "WritoNews ID must be zero or positive")
    @JsonProperty("writonews_id")
    private Long writonewsId;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}