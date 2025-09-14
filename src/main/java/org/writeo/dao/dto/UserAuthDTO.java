//package org.writeo.dao.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.writeo.utils.CommonConstants.CommonConstants;
//import lombok.*;
//
//import jakarta.validation.constraints.*;
//import java.util.Date;
//
//@Builder
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
//@JsonIgnoreProperties(ignoreUnknown = true)
////public class UserAuthDTO {
////
////    @NotNull(message = "User ID must not be null")
////    @Size(max = 255, message = "User ID must not exceed 255 characters")
////    @JsonProperty("user_id")
////    private String userId;
////
////    @NotNull(message = "Email must not be null")
////    @Email(message = "Invalid email format")
////    @Size(max = 255, message = "Email must not exceed 255 characters")
////    @JsonProperty("email")
////    private String email;
////
////    @NotNull(message = "Password must not be null")
////    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
////    @JsonProperty("password")
////    private String password;
////
////    @JsonProperty("last_login")
////    private Date lastLogin;
////
////    @Size(max = 255, message = "Last login device must not exceed 255 characters")
////    @JsonProperty("last_login_device")
////    private String lastLoginDevice;
////
////    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
////    private String key;
////
////}
////
//
//
//
//public class UserAuthDTO {
//
//    @Size(max = 255, message = "User ID must not exceed 255 characters")
//    @JsonProperty("user_id")
//    private String userId;  // Removed @NotNull as it will be generated
//
//    @NotNull(message = "Email must not be null")
//    @Email(message = "Invalid email format")
//    @Size(max = 255, message = "Email must not exceed 255 characters")
//    @JsonProperty("email")
//    private String email;
//
//    @NotNull(message = "Password must not be null")
//    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
//    @JsonProperty("password")
//    private String password;
//
//    @JsonProperty("last_login")
//    private Date lastLogin;
//
//    @Size(max = 255, message = "Last login device must not exceed 255 characters")
//    @JsonProperty("last_login_device")
//    private String lastLoginDevice;
//
//    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
//    private String key;
//}
package org.writeo.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.*;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthDTO {
    @Size(max = 255, message = "User ID must not exceed 255 characters")
    @JsonProperty("user_id")
    private String userId;

    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("last_login")
    private Date lastLogin;

    @Size(max = 255, message = "Last login device must not exceed 255 characters")
    @JsonProperty("last_login_device")
    private String lastLoginDevice;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}