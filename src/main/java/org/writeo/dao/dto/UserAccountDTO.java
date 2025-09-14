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
public class UserAccountDTO {

    @NotBlank(message = "First name must not be blank")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    @JsonProperty("fname")
    private String fname;

    @NotBlank(message = "Last name must not be blank")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    @JsonProperty("lname")
    private String lname;

    @NotBlank(message = "User ID must not be blank")
    @Size(max = 255, message = "User ID must not exceed 255 characters")
    @JsonProperty("user_id")
    private String userId;

    @Size(max = 255, message = "Alias must not exceed 255 characters")
    @JsonProperty("alias")
    private String alias;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}