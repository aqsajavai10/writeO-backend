//package org.writeo.web;
//
//import jakarta.annotation.security.RolesAllowed;
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
//import org.keycloak.representations.AccessToken;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.writeo.dao.dto.UserAuthDTO;
//import org.writeo.service.UserAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//
//import java.security.Principal;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/userauth")
//public class UserAuthController {
//
//    private final UserAuthService userAuthService;
//    @Autowired
//    public UserAuthController(UserAuthService userAuthService) {
//        this.userAuthService = userAuthService;
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public ResponseEntity<UserAuthDTO> createUserAuth(@Valid @RequestBody UserAuthDTO userAuthDTO) {
//        UserAuthDTO createdUserAuth = userAuthService.createUserAuth(userAuthDTO);
//        return new ResponseEntity<>(createdUserAuth, HttpStatus.CREATED);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserAuthDTO> getUserAuthById(@PathVariable String userId) {
//        return userAuthService.getUserAuthById(userId)
//                .map(userAuthDTO -> new ResponseEntity<>(userAuthDTO, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/email/{email}")
//    public ResponseEntity<UserAuthDTO> getUserAuthByEmail(@PathVariable String email) {
//        return userAuthService.getUserAuthByEmail(email)
//                .map(userAuthDTO -> new ResponseEntity<>(userAuthDTO, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{userId}")
//    public ResponseEntity<UserAuthDTO> updateUserAuth(@PathVariable String userId, @Valid @RequestBody UserAuthDTO userAuthDTO) {
//        userAuthDTO.setUserId(userId);
//        UserAuthDTO updatedUserAuth = userAuthService.updateUserAuth(userAuthDTO);
//        return new ResponseEntity<>(updatedUserAuth, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUserAuth(@PathVariable String userId) {
//        userAuthService.deleteUserAuth(userId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping
//    public ResponseEntity<List<UserAuthDTO>> getAllUserAuths() {
//        List<UserAuthDTO> userAuths = userAuthService.getAllUserAuths();
//        return new ResponseEntity<>(userAuths, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/paginated")
//    public ResponseEntity<Page<UserAuthDTO>> getAllUserAuthsPaginated(Pageable pageable) {
//        Page<UserAuthDTO> userAuths = userAuthService.getAllUserAuthsPaginated(pageable);
//        return new ResponseEntity<>(userAuths, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/logged-in-since")
//    public ResponseEntity<List<UserAuthDTO>> getUserAuthsLoggedInSince(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
//        List<UserAuthDTO> userAuths = userAuthService.getUserAuthsLoggedInSince(date);
//        return new ResponseEntity<>(userAuths, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/by-device/{device}")
//    public ResponseEntity<List<UserAuthDTO>> getUserAuthsByLastLoginDevice(@PathVariable String device) {
//        List<UserAuthDTO> userAuths = userAuthService.getUserAuthsByLastLoginDevice(device);
//        return new ResponseEntity<>(userAuths, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/inactive")
//    public ResponseEntity<List<UserAuthDTO>> getInactiveUsers(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
//        List<UserAuthDTO> inactiveUsers = userAuthService.getInactiveUsers(date);
//        return new ResponseEntity<>(inactiveUsers, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/email-exists/{email}")
//    public ResponseEntity<Boolean> isEmailRegistered(@PathVariable String email) {
//        boolean isRegistered = userAuthService.isEmailRegistered(email);
//        return new ResponseEntity<>(isRegistered, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/count-active")
//    public ResponseEntity<Long> countActiveUsersBetweenDates(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
//        long count = userAuthService.countActiveUsersBetweenDates(startDate, endDate);
//        return new ResponseEntity<>(count, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/login")
//    public ResponseEntity<UserAuthDTO> login(@RequestBody LoginRequest loginRequest) {
//        if (userAuthService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword())) {
//            UserAuthDTO userAuthDTO = userAuthService.getUserAuthByEmail(loginRequest.getEmail()).orElseThrow();
//            userAuthDTO = userAuthService.updateLastLogin(userAuthDTO.getUserId(), loginRequest.getDevice());
//            return new ResponseEntity<>(userAuthDTO, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    // Inner class for login request
//    private static class LoginRequest {
//        private String email;
//        private String password;
//        private String device;
//
//        // Getters and setters
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//        public String getDevice() { return device; }
//        public void setDevice(String device) { this.device = device; }
//    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER')")
//    public String userAccess() {
//        return "Hello, User!";
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String adminAccess() {
//        return "Hello, Admin!";
//    }
//
//    @GetMapping("/common")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public String commonAccess() {
//        return "Hello, User or Admin!";
//    }
//
//    @GetMapping("/special")
//    @PreAuthorize("hasRole('ADMIN') and principal.username == 'specialUser'")
//    public String specialAccess() {
//        return "Hello, special admin!";
//    }
//}
package org.writeo.web;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.writeo.dao.dto.UserAuthDTO;
import org.writeo.service.UserAuthService;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/userauth")
@Slf4j
public class UserAuthController {

    private final UserAuthService userAuthService;

//    @lombok.Data
//    public static class LoginRequest {
//        private String email;
//        private String device;
//    }

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody UserAuthDTO userAuthDTO) {
//        try {
//            log.debug("Received registration request for email: {}", userAuthDTO.getEmail());
//            UserAuthDTO registeredUser = userAuthService.createUserAuth(userAuthDTO);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("userId", registeredUser.getUserId());
//            response.put("email", registeredUser.getEmail());
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            log.error("Registration failed", e);
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("success", false);
//            errorResponse.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(errorResponse);
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserAuthDTO userAuthDTO) {
        try {
            log.debug("Received registration request for email: {}", userAuthDTO.getEmail());
            UserAuthDTO registeredUser = userAuthService.createUserAuth(userAuthDTO);

            // Generate tokens for the newly registered user
            Map<String, String> tokens = userAuthService.authenticateAndGenerateTokens(
                    userAuthDTO.getEmail(),
                    userAuthDTO.getPassword()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("userId", registeredUser.getUserId());
            response.put("email", registeredUser.getEmail());
            response.put("accessToken", tokens.get("accessToken"));
            response.put("refreshToken", tokens.get("refreshToken"));
            response.put("tokenType", "Bearer");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Registration failed", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
//    @PostMapping("/login")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, Principal principal) {
//        try {
//            // Get the authenticated user's email from the token
//            KeycloakAuthenticationToken keycloakAuth = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//            AccessToken token = keycloakAuth.getAccount().getKeycloakSecurityContext().getToken();
//            String authenticatedEmail = token.getEmail();
//
//            // Verify that the request email matches the authenticated user
//            if (!authenticatedEmail.equals(loginRequest.getEmail())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body(Map.of("message", "Email mismatch with authenticated user"));
//            }
//
//            // Get user from database and update last login
//            Optional<UserAuthDTO> userOpt = userAuthService.getUserAuthByEmail(loginRequest.getEmail());
//            if (userOpt.isPresent()) {
//                UserAuthDTO user = userAuthService.updateLastLogin(
//                        userOpt.get().getUserId(),
//                        loginRequest.getDevice()
//                );
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("success", true);
//                response.put("userId", user.getUserId());
//                response.put("email", user.getEmail());
//                response.put("lastLogin", user.getLastLogin());
//
//                return ResponseEntity.ok(response);
//            }
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "User not found"));
//        } catch (Exception e) {
//            log.error("Login error", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Login failed: " + e.getMessage()));
//        }
//    }

//    @PostMapping("/login")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, Authentication authentication) {
//        try {
//            String authenticatedEmail = extractEmailFromAuthentication(authentication);
//
//            if (authenticatedEmail == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("message", "Email claim not found in token"));
//            }
//
//            // Verify that the request email matches the authenticated user
//            if (!authenticatedEmail.equals(loginRequest.getEmail())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body(Map.of("message", "Email mismatch with authenticated user"));
//            }
//
//            // Get user from database and update last login
//            Optional<UserAuthDTO> userOpt = userAuthService.getUserAuthByEmail(loginRequest.getEmail());
//            if (userOpt.isPresent()) {
//                UserAuthDTO user = userAuthService.updateLastLogin(
//                        userOpt.get().getUserId(),
//                        loginRequest.getDevice()
//                );
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("success", true);
//                response.put("userId", user.getUserId());
//                response.put("email", user.getEmail());
//                response.put("lastLogin", user.getLastLogin());
//                return ResponseEntity.ok(response);
//            }
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "User not found"));
//
//        } catch (Exception e) {
//            log.error("Login error", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Login failed: " + e.getMessage()));
//        }
//    }
//
//    private String extractEmailFromAuthentication(Authentication authentication) {
//        try {
//            if (authentication instanceof KeycloakAuthenticationToken) {
//                KeycloakAuthenticationToken keycloakAuth = (KeycloakAuthenticationToken) authentication;
//                return keycloakAuth.getAccount().getKeycloakSecurityContext().getToken().getEmail();
//            } else if (authentication.getPrincipal() instanceof Jwt) {
//                Jwt jwt = (Jwt) authentication.getPrincipal();
//                return jwt.getClaimAsString("email");
//            } else {
//                log.error("Unsupported authentication type: {}", authentication.getClass().getName());
//                throw new IllegalArgumentException("Unsupported authentication type");
//            }
//        } catch (Exception e) {
//            log.error("Error extracting email from authentication", e);
//            throw e;
//        }
//    }

//    @PostMapping("/login")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, Authentication authentication) {
//        try {
//            // Get the authenticated user's details from JWT
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            String authenticatedEmail = jwt.getClaimAsString("preferred_username");
//
//            // If preferred_username is null, try email claim
//            if (authenticatedEmail == null) {
//                authenticatedEmail = jwt.getClaimAsString("email");
//            }
//
//            if (authenticatedEmail == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("message", "Email claim not found in token"));
//            }
//
//            // Get user from database and update last login
//            Optional<UserAuthDTO> userOpt = userAuthService.getUserAuthByEmail(loginRequest.getEmail());
//            if (userOpt.isPresent()) {
//                UserAuthDTO user = userAuthService.updateLastLogin(
//                        userOpt.get().getUserId(),
//                        loginRequest.getDevice()
//                );
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("success", true);
//                response.put("userId", user.getUserId());
//                response.put("email", user.getEmail());
//                response.put("lastLogin", user.getLastLogin());
//
//                return ResponseEntity.ok(response);
//            }
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "User not found"));
//        } catch (Exception e) {
//            log.error("Login error", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Login failed: " + e.getMessage()));
//        }
//    }

//    @PostMapping("/login")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, Authentication authentication) {
//        try {
//            log.debug("Login attempt for email: {}", loginRequest.getEmail());
//            log.debug("Authentication details: {}", authentication);
//
//            // Get JWT from authentication
//            if (!(authentication.getPrincipal() instanceof Jwt)) {
//                log.error("Principal is not a JWT token");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("message", "Invalid authentication type"));
//            }
//
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            log.debug("JWT claims: {}", jwt.getClaims());
//
//            // Try different claim names for email
//            String authenticatedEmail = null;
//            if (jwt.hasClaim("email")) {
//                authenticatedEmail = jwt.getClaimAsString("email");
//            } else if (jwt.hasClaim("preferred_username")) {
//                authenticatedEmail = jwt.getClaimAsString("preferred_username");
//            }
//
//            log.debug("Authenticated email from token: {}", authenticatedEmail);
//            log.debug("Requested login email: {}", loginRequest.getEmail());
//
//            if (authenticatedEmail == null) {
//                log.error("No email claim found in token");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("message", "No email found in token"));
//            }
//
//            // Get user from database
//            Optional<UserAuthDTO> userOpt = userAuthService.getUserAuthByEmail(loginRequest.getEmail());
//            if (userOpt.isPresent()) {
//                UserAuthDTO user = userAuthService.updateLastLogin(
//                        userOpt.get().getUserId(),
//                        loginRequest.getDevice()
//                );
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("success", true);
//                response.put("userId", user.getUserId());
//                response.put("email", user.getEmail());
//                response.put("lastLogin", user.getLastLogin());
//
//                return ResponseEntity.ok(response);
//            }
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "User not found"));
//
//        } catch (Exception e) {
//            log.error("Login error", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Login failed: " + e.getMessage()));
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.debug("Login attempt for email: {}", loginRequest.getEmail());

            // Authenticate and generate tokens
            Map<String, String> tokens = userAuthService.authenticateAndGenerateTokens(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            // Get user details and update last login
            Optional<UserAuthDTO> userOpt = userAuthService.getUserAuthByEmail(loginRequest.getEmail());
            if (userOpt.isPresent()) {
                UserAuthDTO user = userAuthService.updateLastLogin(
                        userOpt.get().getUserId(),
                        loginRequest.getDevice()
                );

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("userId", user.getUserId());
                response.put("email", user.getEmail());
                response.put("lastLogin", user.getLastLogin());
                response.put("accessToken", tokens.get("accessToken"));
                response.put("refreshToken", tokens.get("refreshToken"));
                response.put("tokenType", "Bearer");

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));

        } catch (Exception e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            Map<String, String> tokens = userAuthService.refreshTokens(request.getRefreshToken());

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", tokens.get("accessToken"));
            response.put("tokenType", "Bearer");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Token refresh failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token refresh failed: " + e.getMessage()));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
        private String device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshTokenRequest {
        private String refreshToken;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserAuthDTO> createUserAuth(@Valid @RequestBody UserAuthDTO userAuthDTO) {
        UserAuthDTO createdUserAuth = userAuthService.createUserAuth(userAuthDTO);
        return new ResponseEntity<>(createdUserAuth, HttpStatus.CREATED);
    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserAuthDTO> getUserAuthById(@PathVariable String userId) {
//        return userAuthService.getUserAuthById(userId)
//                .map(userAuthDTO -> new ResponseEntity<>(userAuthDTO, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Protected endpoints requiring authentication
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserAuthDTO> getUserAuthById(
            @PathVariable String userId,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token and validate
            String token = authHeader.substring(7);
            String email = userAuthService.extractUsername(token);

            if (!userAuthService.isTokenValid(token, email)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return userAuthService.getUserAuthById(userId)
                    .map(userAuthDTO -> new ResponseEntity<>(userAuthDTO, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error getting user by ID", e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "message", "Invalid token format"));
            }

            String token = authHeader.substring(7);
            String email = userAuthService.extractUsername(token);
            boolean isValid = userAuthService.isTokenValid(token, email);

            return ResponseEntity.ok(Map.of("valid", isValid));
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthDTO> getUserAuthByEmail(@PathVariable String email) {
        return userAuthService.getUserAuthByEmail(email)
                .map(userAuthDTO -> new ResponseEntity<>(userAuthDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserAuthDTO> updateUserAuth(@PathVariable String userId, @Valid @RequestBody UserAuthDTO userAuthDTO) {
        userAuthDTO.setUserId(userId);
        UserAuthDTO updatedUserAuth = userAuthService.updateUserAuth(userAuthDTO);
        return new ResponseEntity<>(updatedUserAuth, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserAuth(@PathVariable String userId) {
        userAuthService.deleteUserAuth(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<UserAuthDTO>> getAllUserAuths() {
        List<UserAuthDTO> userAuths = userAuthService.getAllUserAuths();
        return new ResponseEntity<>(userAuths, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paginated")
    public ResponseEntity<Page<UserAuthDTO>> getAllUserAuthsPaginated(Pageable pageable) {
        Page<UserAuthDTO> userAuths = userAuthService.getAllUserAuthsPaginated(pageable);
        return new ResponseEntity<>(userAuths, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/logged-in-since")
    public ResponseEntity<List<UserAuthDTO>> getUserAuthsLoggedInSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<UserAuthDTO> userAuths = userAuthService.getUserAuthsLoggedInSince(date);
        return new ResponseEntity<>(userAuths, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/login")
//    public ResponseEntity<UserAuthDTO> login(@RequestBody LoginRequest loginRequest) {
//        if (userAuthService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword())) {
//            UserAuthDTO userAuthDTO = userAuthService.getUserAuthByEmail(loginRequest.getEmail()).orElseThrow();
//            userAuthDTO = userAuthService.updateLastLogin(userAuthDTO.getUserId(), loginRequest.getDevice());
//            return new ResponseEntity<>(userAuthDTO, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/by-device/{device}")
    public ResponseEntity<List<UserAuthDTO>> getUserAuthsByLastLoginDevice(@PathVariable String device) {
        List<UserAuthDTO> userAuths = userAuthService.getUserAuthsByLastLoginDevice(device);
        return new ResponseEntity<>(userAuths, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactive")
    public ResponseEntity<List<UserAuthDTO>> getInactiveUsers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<UserAuthDTO> inactiveUsers = userAuthService.getInactiveUsers(date);
        return new ResponseEntity<>(inactiveUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/email-exists/{email}")
    public ResponseEntity<Boolean> isEmailRegistered(@PathVariable String email) {
        boolean isRegistered = userAuthService.isEmailRegistered(email);
        return new ResponseEntity<>(isRegistered, HttpStatus.OK);
    }

    // Inner class for login request
//    private static class LoginRequest {
//        private String email;
//        private String password;
//        private String device;
//
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//        public String getDevice() { return device; }
//        public void setDevice(String device) { this.device = device; }
//    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "Hello, User!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Hello, Admin!";
    }

    @GetMapping("/common")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String commonAccess() {
        return "Hello, User or Admin!";
    }

    @GetMapping("/special")
    @PreAuthorize("hasRole('ADMIN') and principal.username == 'specialUser'")
    public String specialAccess() {
        return "Hello, special admin!";
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Backend is working");
        return ResponseEntity.ok(response);
    }
//
//    private static class LoginRequest {
//        private String email;
//        private String device;
//
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getDevice() { return device; }
//        public void setDevice(String device) { this.device = device; }
//    }
}
