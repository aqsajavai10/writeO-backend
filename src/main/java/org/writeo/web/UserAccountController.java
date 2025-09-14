package org.writeo.web;

import org.writeo.dao.dto.UserAccountDTO;
import org.writeo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;

import jakarta.validation.Valid;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;

@RestController
@RequestMapping(value = "/useraccounts")
@Log4j2
@AllArgsConstructor
public class UserAccountController {

    @Autowired
    private final UserAccountService userAccountService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllUserAccounts() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all user accounts." : null);
            List<UserAccountDTO> userAccounts = userAccountService.findAll();
            log.info(log.isInfoEnabled() ? "User accounts fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch user accounts: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{userId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getUserAccountById(@PathVariable String userId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch user account by id: {}." : null, userId);
            UserAccountDTO userAccount = userAccountService.findById(userId);
            log.info(log.isInfoEnabled() ? "User account fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccount);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch user account: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addUserAccount(@Valid @RequestBody UserAccountDTO userAccount) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a user account." : null);
            UserAccountDTO addedUserAccount = userAccountService.insert(userAccount);
            log.info(log.isInfoEnabled() ? "User account added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedUserAccount);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add user account: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateUserAccount(@Valid @RequestBody UserAccountDTO userAccount) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a user account." : null);
            UserAccountDTO updatedUserAccount = userAccountService.update(userAccount);
            log.info(log.isInfoEnabled() ? "User account updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedUserAccount);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update user account: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{userId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteUserAccount(@PathVariable String userId) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete a user account." : null);
            userAccountService.delete(userId);
            log.info(log.isInfoEnabled() ? "User account deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("User account deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete user account: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByFname/{fname}", produces = "application/json")
    public ResponseEntity<BackendResponse> getUserAccountsByFname(@PathVariable String fname) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch user accounts by first name: {}." : null, fname);
            List<UserAccountDTO> userAccounts = userAccountService.findAllByFname(fname);
            log.info(log.isInfoEnabled() ? "User accounts fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch user accounts: {}" : null, e.getMessage());
            throw e;
        }
    }


    @GetMapping(value = "/getByLname/{lname}", produces = "application/json")
    public ResponseEntity<BackendResponse> getUserAccountsByLname(@PathVariable String lname) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch user accounts by last name: {}." : null, lname);
            List<UserAccountDTO> userAccounts = userAccountService.findAllByLname(lname);
            log.info(log.isInfoEnabled() ? "User accounts fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch user accounts: {}" : null, e.getMessage());
            throw e;
        }
    }


    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<BackendResponse> searchUserAccounts(@RequestParam String name) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to search user accounts by name: {}." : null, name);
            List<UserAccountDTO> userAccounts = userAccountService.findByNameContaining(name);
            log.info(log.isInfoEnabled() ? "User accounts searched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to search user accounts: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/sorted", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllUserAccountsSorted() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all user accounts sorted by name." : null);
            List<UserAccountDTO> userAccounts = userAccountService.getAllUserAccountsSortedByNameAsc();
            log.info(log.isInfoEnabled() ? "User accounts fetched and sorted successfully." : null);
            return ResponseHandlerUtil.handleSuccess(userAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch and sort user accounts: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/count", produces = "application/json")
    public ResponseEntity<BackendResponse> getTotalUserAccountsCount() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to get total count of user accounts." : null);
            Long count = userAccountService.getTotalUserAccountsCount();
            log.info(log.isInfoEnabled() ? "Total user accounts count fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(count);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to get total user accounts count: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/checkAlias/{alias}", produces = "application/json")
    public ResponseEntity<BackendResponse> checkAliasExists(@PathVariable String alias) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to check if alias exists: {}." : null, alias);
            boolean exists = userAccountService.existsByAlias(alias);
            log.info(log.isInfoEnabled() ? "Alias existence checked successfully." : null);
            return ResponseHandlerUtil.handleSuccess(exists);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to check alias existence: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/addBatch", produces = "application/json")
    public ResponseEntity<BackendResponse> addUserAccountsBatch(@Valid @RequestBody List<UserAccountDTO> userAccounts) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a batch of user accounts." : null);
            List<UserAccountDTO> addedUserAccounts = userAccountService.insertBatch(userAccounts);
            log.info(log.isInfoEnabled() ? "User accounts batch added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedUserAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add user accounts batch: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/updateBatch", produces = "application/json")
    public ResponseEntity<BackendResponse> updateUserAccountsBatch(@Valid @RequestBody List<UserAccountDTO> userAccounts) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a batch of user accounts." : null);
            List<UserAccountDTO> updatedUserAccounts = userAccountService.updateBatch(userAccounts);
            log.info(log.isInfoEnabled() ? "User accounts batch updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedUserAccounts);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update user accounts batch: {}" : null, e.getMessage());
            throw e;
        }
    }
}