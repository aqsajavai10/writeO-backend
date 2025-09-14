//package org.writeo.service.impl;
//
//import org.writeo.dao.dto.UserAuthDTO;
//import org.writeo.dao.mapper.UserAuthMapper;
//import org.writeo.dao.model.UserAuth;
//import org.writeo.dao.repository.UserAuthRepository;
//import org.writeo.service.UserAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class UserAuthServiceImpl implements UserAuthService {
//
//    private final UserAuthRepository userAuthRepository;
//    private final UserAuthMapper userAuthMapper;
//
//    @Autowired
//    public UserAuthServiceImpl(UserAuthRepository userAuthRepository, UserAuthMapper userAuthMapper) {
//        this.userAuthRepository = userAuthRepository;
//        this.userAuthMapper = userAuthMapper;
//    }

package org.writeo.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.writeo.config.JwtPropertiesConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.writeo.config.JwtPropertiesConfiguration;
import org.writeo.dao.dto.UserAuthDTO;
import org.writeo.dao.mapper.UserAuthMapper;
import org.writeo.dao.model.UserAuth;
import org.writeo.dao.repository.UserAuthRepository;
import org.writeo.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.writeo.dao.dto.UserAuthDTO;
import org.writeo.dao.mapper.UserAuthMapper;
import org.writeo.dao.model.UserAuth;
import org.writeo.dao.repository.UserAuthRepository;
import org.writeo.service.UserAuthService;
//import org.writeo.service.JwtService;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

//    private final UserAuthRepository userAuthRepository;
//    private final UserAuthMapper userAuthMapper;
//    private final Keycloak keycloakAdmin;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Autowired
//    public UserAuthServiceImpl(
//            UserAuthRepository userAuthRepository,
//            UserAuthMapper userAuthMapper,
//            Keycloak keycloakAdmin) {
//        this.userAuthRepository = userAuthRepository;
//        this.userAuthMapper = userAuthMapper;
//        this.keycloakAdmin = keycloakAdmin;
//    }


//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.access-token.expiration}")
//    private long jwtExpiration;
//
//    @Value("${jwt.refresh-token.expiration}")
//    private long refreshExpiration;


    private final UserAuthRepository userAuthRepository;
    private final UserAuthMapper userAuthMapper;
    private final Keycloak keycloakAdmin;
    private final JwtPropertiesConfiguration jwtProperties;

    @Value("${keycloak.realm}")
    private String realm;


    public UserAuthServiceImpl(
            UserAuthRepository userAuthRepository,
            UserAuthMapper userAuthMapper,
            Keycloak keycloakAdmin,
            JwtPropertiesConfiguration jwtProperties) {
        this.userAuthRepository = userAuthRepository;
        this.userAuthMapper = userAuthMapper;
        this.keycloakAdmin = keycloakAdmin;
        this.jwtProperties = jwtProperties;
    }

    // JWT related methods
    @Override
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtProperties.getJwtExpiration());
    }

    @Override
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    @Override
    public String generateRefreshToken(String username) {
        return buildToken(new HashMap<>(), username, jwtProperties.getRefreshExpiration());
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return null;
    }

    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtProperties.getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    @Override
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    @Override
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Add new authentication method that returns tokens
    @Transactional
    public Map<String, String> authenticateAndGenerateTokens(String email, String password) {
        try {
            if (authenticateUser(email, password)) {
                String accessToken = generateToken(email);
                String refreshToken = generateRefreshToken(email);

                // Update last login
                Optional<UserAuth> userOpt = userAuthRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    updateLastLogin(userOpt.get().getUserId(), null);
                }

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                return tokens;
            }
            throw new RuntimeException("Invalid credentials");
        } catch (Exception e) {
            log.error("Authentication failed", e);
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    // Add token refresh method
    @Transactional
    public Map<String, String> refreshTokens(String refreshToken) {
        try {
            String email = extractUsername(refreshToken);
            if (email != null && userAuthRepository.findByEmail(email).isPresent()) {
                String newAccessToken = generateToken(email);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                tokens.put("refreshToken", refreshToken); // Keep the same refresh token
                return tokens;
            }
            throw new RuntimeException("Invalid refresh token");
        } catch (Exception e) {
            log.error("Token refresh failed", e);
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    @Override
    @Transactional
    public UserAuthDTO createUserAuth(UserAuthDTO userAuthDTO) {
//        UserAuth userAuth = userAuthMapper.dtoToEntity(userAuthDTO);
//        userAuth.setPassword(hashPassword(userAuth.getPassword()));
//        UserAuth savedUserAuth = userAuthRepository.save(userAuth);
//        return userAuthMapper.entityToDto(savedUserAuth);
        // First create user in Keycloak
        String keycloakUserId = createKeycloakUser(userAuthDTO);

        // Set the Keycloak user ID as our user ID
        userAuthDTO.setUserId(keycloakUserId);

        // Then create user in our database
        UserAuth userAuth = userAuthMapper.dtoToEntity(userAuthDTO);
        userAuth.setPassword(hashPassword(userAuth.getPassword())); // Still hash password for DB
        UserAuth savedUserAuth = userAuthRepository.save(userAuth);

        return userAuthMapper.entityToDto(savedUserAuth);
    }

//    private String createKeycloakUser(UserAuthDTO userAuthDTO) {
//        try {
//            // Create user representation
//            UserRepresentation user = new UserRepresentation();
//            user.setUsername(userAuthDTO.getEmail());
//            user.setEmail(userAuthDTO.getEmail());
//            user.setEnabled(true);
//            user.setEmailVerified(false);
//
//            // Set password credential
//            CredentialRepresentation credential = new CredentialRepresentation();
//            credential.setType(CredentialRepresentation.PASSWORD);
//            credential.setValue(userAuthDTO.getPassword());
//            credential.setTemporary(false);
//            user.setCredentials(Collections.singletonList(credential));
//
//            // Create user in Keycloak
//            Response response = keycloakAdmin.realm(realm).users().create(user);
//
//            if (response.getStatus() >= 200 && response.getStatus() < 300) {
//                // Extract user ID from response
//                String location = response.getHeaderString("Location");
//                if (location != null) {
//                    String[] segments = location.split("/");
//                    return segments[segments.length - 1];
//                }
//            }
//
//            throw new RuntimeException("Failed to create user in Keycloak. Status: " + response.getStatus());
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating user in Keycloak: " + e.getMessage(), e);
//        }
//    }

//    private String createKeycloakUser(UserAuthDTO userAuthDTO) {
//        try {
//            // Create user representation
//            UserRepresentation user = new UserRepresentation();
//            user.setUsername(userAuthDTO.getEmail());
//            user.setEmail(userAuthDTO.getEmail());
//            user.setEnabled(true);
////            user.setEmailVerified(false);
//            user.setEmailVerified(true); // Add this line
//            user.setRequiredActions(Collections.emptyList()); // Add this line
//
//            // Create initial groups or roles if needed
//            List<String> realmRoles = new ArrayList<>();
//            realmRoles.add("USER");  // Add default USER role
//            user.setRealmRoles(realmRoles);
//
//            // Set password credential
//            CredentialRepresentation credential = new CredentialRepresentation();
//            credential.setType(CredentialRepresentation.PASSWORD);
//            credential.setValue(userAuthDTO.getPassword());
//            credential.setTemporary(false);
//            user.setCredentials(Collections.singletonList(credential));
//
//            // Create user in Keycloak
//            Response response = keycloakAdmin.realm(realm).users().create(user);
//
//            if (response.getStatus() == 201) { // Created successfully
//                String locationHeader = response.getHeaderString("Location");
//                String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
//
//                // Assign roles if needed
//                RoleRepresentation userRole = keycloakAdmin.realm(realm).roles().get("USER").toRepresentation();
//                keycloakAdmin.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(userRole));
//
//                return userId;
//            } else if (response.getStatus() == 409) { // Conflict - user already exists
//                throw new RuntimeException("User with this email already exists");
//            } else {
//                log.error("Failed to create user in Keycloak. Status: {}", response.getStatus());
//                throw new RuntimeException("Failed to create user in Keycloak. Status: " + response.getStatus());
//            }
//        } catch (Exception e) {
//            log.error("Error creating user in Keycloak", e);
//            throw new RuntimeException("Error creating user in Keycloak: " + e.getMessage(), e);
//        }
//    }

    private String createKeycloakUser(UserAuthDTO userAuthDTO) {
        try {
            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userAuthDTO.getEmail());
            user.setEmail(userAuthDTO.getEmail());
            user.setEnabled(true);
            user.setEmailVerified(true);  // Set this to true

            // Important: Set empty required actions list
            user.setRequiredActions(new ArrayList<>());

            // Set credential
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(userAuthDTO.getPassword());
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Set default roles
            List<String> realmRoles = new ArrayList<>();
            realmRoles.add("USER");
            user.setRealmRoles(realmRoles);

            // Create user
            Response response = keycloakAdmin.realm(realm).users().create(user);

            if (response.getStatus() == 201) {
                String locationHeader = response.getHeaderString("Location");
                String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);

                // Get user to update required actions
                UserResource userResource = keycloakAdmin.realm(realm).users().get(userId);

                // Clear any required actions
                userResource.update(user);

                // Assign roles
                RoleRepresentation userRole = keycloakAdmin.realm(realm).roles()
                        .get("USER").toRepresentation();
                userResource.roles().realmLevel().add(Collections.singletonList(userRole));

                return userId;
            } else {
                log.error("Failed to create user in Keycloak. Status: {}", response.getStatus());
                throw new RuntimeException("Failed to create user in Keycloak");
            }
        } catch (Exception e) {
            log.error("Error creating user in Keycloak", e);
            throw new RuntimeException("Error creating user in Keycloak: " + e.getMessage(), e);
        }
    }
    // Override authenticateUser to use Keycloak
//    @Override
//    public boolean authenticateUser(String email, String password) {
//        try {
//            // This should be implemented to use Keycloak's token endpoint
//            // For now, we'll keep the database check
//            Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(email);
//            if (userAuthOptional.isPresent()) {
//                UserAuth userAuth = userAuthOptional.get();
//                return userAuth.getPassword().equals(hashPassword(password));
//            }
//            return false;
//        } catch (Exception e) {
//            throw new RuntimeException("Authentication failed", e);
//        }
//    }

    @Override
    public boolean authenticateUser(String email, String password) {
        try {
            // First try to find the user in our database
            Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(email);
            if (!userAuthOptional.isPresent()) {
                log.error("User not found in database: {}", email);
                return false;
            }

            // Get user from Keycloak to verify credentials
            UsersResource usersResource = keycloakAdmin.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(email);

            if (users.isEmpty()) {
                log.error("User not found in Keycloak: {}", email);
                return false;
            }

            // The user exists in both our DB and Keycloak
            UserAuth userAuth = userAuthOptional.get();
            String hashedPassword = hashPassword(password);

            // Compare the hashed password with what's stored in our DB
            if (!userAuth.getPassword().equals(hashedPassword)) {
                log.error("Password mismatch for user: {}", email);
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", email, e);
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<UserAuthDTO> getUserAuthById(String userId) {
        return userAuthRepository.findById(userId).map(userAuthMapper::entityToDto);
    }

    @Override
    public Optional<UserAuthDTO> getUserAuthByEmail(String email) {
        return userAuthRepository.findByEmail(email).map(userAuthMapper::entityToDto);
    }

//    @Override
//    @Transactional
//    public UserAuthDTO updateUserAuth(UserAuthDTO userAuthDTO) {
//        UserAuth existingUserAuth = userAuthRepository.findById(userAuthDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("UserAuth not found"));
//        UserAuth updatedUserAuth = userAuthMapper.existingEntityToNewEntity(userAuthDTO, existingUserAuth);
//        if (userAuthDTO.getPassword() != null && !userAuthDTO.getPassword().isEmpty()) {
//            updatedUserAuth.setPassword(hashPassword(userAuthDTO.getPassword()));
//        }
//        return userAuthMapper.entityToDto(userAuthRepository.save(updatedUserAuth));
//    }

    @Override
    @Transactional
    public UserAuthDTO updateUserAuth(UserAuthDTO userAuthDTO) {
        UserAuth existingUserAuth = userAuthRepository.findById(userAuthDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("UserAuth not found"));

        // Update user in Keycloak if password is changing
        if (userAuthDTO.getPassword() != null && !userAuthDTO.getPassword().isEmpty()) {
            updateKeycloakUserPassword(userAuthDTO.getUserId(), userAuthDTO.getPassword());
        }

        UserAuth updatedUserAuth = userAuthMapper.existingEntityToNewEntity(userAuthDTO, existingUserAuth);
        if (userAuthDTO.getPassword() != null && !userAuthDTO.getPassword().isEmpty()) {
            updatedUserAuth.setPassword(hashPassword(userAuthDTO.getPassword()));
        }
        return userAuthMapper.entityToDto(userAuthRepository.save(updatedUserAuth));
    }

    private void updateKeycloakUserPassword(String userId, String newPassword) {
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword);
            credential.setTemporary(false);

            keycloakAdmin.realm(realm).users().get(userId).resetPassword(credential);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password in Keycloak", e);
        }
    }
    @Override
    @Transactional
    public void deleteUserAuth(String userId) {
        userAuthRepository.deleteById(userId);
    }

    @Override
    public List<UserAuthDTO> getAllUserAuths() {
        return userAuthRepository.findAll().stream()
                .map(userAuthMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserAuthDTO> getAllUserAuthsPaginated(Pageable pageable) {
        return userAuthRepository.findAll(pageable).map(userAuthMapper::entityToDto);
    }

    @Override
    public List<UserAuthDTO> getUserAuthsLoggedInSince(Date date) {
        return userAuthRepository.findAllLoggedInSince(date).stream()
                .map(userAuthMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAuthDTO> getUserAuthsByLastLoginDevice(String device) {
        return userAuthRepository.findAllByLastLoginDevice(device).stream()
                .map(userAuthMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAuthDTO> getInactiveUsers(Date date) {
        return userAuthRepository.findInactiveUsers(date).stream()
                .map(userAuthMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userAuthRepository.existsByEmail(email);
    }

    @Override
    public long countActiveUsersBetweenDates(Date startDate, Date endDate) {
        return userAuthRepository.countActiveUsersBetweenDates(startDate, endDate);
    }

//    @Override
//    @Transactional
//    public UserAuthDTO updateLastLogin(String userId, String lastLoginDevice) {
//        UserAuth userAuth = userAuthRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("UserAuth not found"));
//        userAuth.setLastLogin(new Date());
//        userAuth.setLastLoginDevice(lastLoginDevice);
//        return userAuthMapper.entityToDto(userAuthRepository.save(userAuth));
//    }

    @Override
    @Transactional
    public UserAuthDTO updateLastLogin(String userId, String lastLoginDevice) {
        try {
            UserAuth userAuth = userAuthRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userAuth.setLastLogin(new Date());
            if (lastLoginDevice != null && !lastLoginDevice.isEmpty()) {
                userAuth.setLastLoginDevice(lastLoginDevice);
            }

            UserAuth updatedUser = userAuthRepository.save(userAuth);
            return userAuthMapper.entityToDto(updatedUser);
        } catch (Exception e) {
            log.error("Error updating last login", e);
            throw new RuntimeException("Failed to update last login information");
        }
    }
//    @Override
//    public boolean authenticateUser(String email, String password) {
//        // Keycloak authentication logic or integrate with Keycloak token
//        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(email);
//        if (userAuthOptional.isPresent()) {
//            UserAuth userAuth = userAuthOptional.get();
//            // Logic to check if the password matches
//            return userAuth.getPassword().equals(hashPassword(password));
//        }
//        return false;
//    }



}