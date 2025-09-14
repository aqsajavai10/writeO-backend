//package org.writeo.service;
//
//import org.writeo.dao.dto.UserAuthDTO;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//public interface UserAuthService {
//
//    UserAuthDTO createUserAuth(UserAuthDTO userAuthDTO);
//
//    Optional<UserAuthDTO> getUserAuthById(String userId);
//
//    Optional<UserAuthDTO> getUserAuthByEmail(String email);
//
//    UserAuthDTO updateUserAuth(UserAuthDTO userAuthDTO);
//
//    void deleteUserAuth(String userId);
//
//    List<UserAuthDTO> getAllUserAuths();
//
//    Page<UserAuthDTO> getAllUserAuthsPaginated(Pageable pageable);
//
//    List<UserAuthDTO> getUserAuthsLoggedInSince(Date date);
//
//    List<UserAuthDTO> getUserAuthsByLastLoginDevice(String device);
//
//    List<UserAuthDTO> getInactiveUsers(Date date);
//
//    boolean isEmailRegistered(String email);
//
//    long countActiveUsersBetweenDates(Date startDate, Date endDate);
//
//    UserAuthDTO updateLastLogin(String userId, String lastLoginDevice);
//
//    boolean authenticateUser(String email, String password);
//
//    //UserAuthDTO syncUserWithKeycloak(String userId, String email);
//
//}

package org.writeo.service;

import org.writeo.dao.dto.UserAuthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface UserAuthService {
    // Existing methods
    UserAuthDTO createUserAuth(UserAuthDTO userAuthDTO);
    Optional<UserAuthDTO> getUserAuthById(String userId);
    Optional<UserAuthDTO> getUserAuthByEmail(String email);
    UserAuthDTO updateUserAuth(UserAuthDTO userAuthDTO);
    void deleteUserAuth(String userId);
    List<UserAuthDTO> getAllUserAuths();
    Page<UserAuthDTO> getAllUserAuthsPaginated(Pageable pageable);
    List<UserAuthDTO> getUserAuthsLoggedInSince(Date date);
    List<UserAuthDTO> getUserAuthsByLastLoginDevice(String device);
    List<UserAuthDTO> getInactiveUsers(Date date);
    boolean isEmailRegistered(String email);
    long countActiveUsersBetweenDates(Date startDate, Date endDate);
    UserAuthDTO updateLastLogin(String userId, String lastLoginDevice);
    boolean authenticateUser(String email, String password);

    // JWT related methods
    String generateToken(Map<String, Object> extraClaims, String username);
    String generateToken(String username);
    String generateRefreshToken(String username);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token, String username);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);

    // Authentication with tokens
    Map<String, String> authenticateAndGenerateTokens(String email, String password);
    Map<String, String> refreshTokens(String refreshToken);
}