package org.writeo.web;

import org.writeo.dao.dto.CharacteristicsDTO;
import org.writeo.service.CharacteristicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;

import jakarta.validation.Valid;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;

@RestController
@RequestMapping(value = "/characteristics")
@Log4j2
@AllArgsConstructor
public class CharacteristicsController {

    @Autowired
    private final CharacteristicsService characteristicsService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllCharacteristics() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all characteristics." : null);
            List<CharacteristicsDTO> characteristics = characteristicsService.findAll();
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacteristicsById(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characteristics by id: {}." : null, id);
            CharacteristicsDTO characteristics = characteristicsService.findById(id);
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByRace/{race}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacteristicsByRace(@PathVariable String race) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characteristics by race: {}." : null, race);
            List<CharacteristicsDTO> characteristics = characteristicsService.findAllByRace(race);
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addCharacteristics(@Valid @RequestBody CharacteristicsDTO characteristics) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add characteristics." : null);
            CharacteristicsDTO addedCharacteristics = characteristicsService.insert(characteristics);
            log.info(log.isInfoEnabled() ? "Characteristics added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedCharacteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateCharacteristics(@Valid @RequestBody CharacteristicsDTO characteristics) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update characteristics." : null);
            CharacteristicsDTO updatedCharacteristics = characteristicsService.update(characteristics);
            log.info(log.isInfoEnabled() ? "Characteristics updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedCharacteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteCharacteristics(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete characteristics." : null);
            characteristicsService.delete(id);
            log.info(log.isInfoEnabled() ? "Characteristics deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Characteristics deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByType/{type}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacteristicsByType(@PathVariable String type) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characteristics by type: {}." : null, type);
            List<CharacteristicsDTO> characteristics = characteristicsService.findAllByType(type);
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByMinLevel/{minLevel}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacteristicsByMinLevel(@PathVariable Integer minLevel) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characteristics by minimum level: {}." : null, minLevel);
            List<CharacteristicsDTO> characteristics = characteristicsService.findAllByLevelGreaterThanEqual(minLevel);
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByMinEnergy/{minEnergy}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacteristicsByMinEnergy(@PathVariable Integer minEnergy) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characteristics by minimum energy: {}." : null, minEnergy);
            List<CharacteristicsDTO> characteristics = characteristicsService.findAllByEnergyGreaterThanEqual(minEnergy);
            log.info(log.isInfoEnabled() ? "Characteristics fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/searchByReputation", produces = "application/json")
    public ResponseEntity<BackendResponse> searchCharacteristicsByReputation(@RequestParam String reputation) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to search characteristics by reputation: {}." : null, reputation);
            List<CharacteristicsDTO> characteristics = characteristicsService.findByReputationContaining(reputation);
            log.info(log.isInfoEnabled() ? "Characteristics searched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to search characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/sortedByLevel", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllCharacteristicsSortedByLevel() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all characteristics sorted by level." : null);
            List<CharacteristicsDTO> characteristics = characteristicsService.getAllCharacteristicsSortedByLevelDesc();
            log.info(log.isInfoEnabled() ? "Characteristics fetched and sorted successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch and sort characteristics: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/averageLevelByRace/{race}", produces = "application/json")
    public ResponseEntity<BackendResponse> getAverageLevelByRace(@PathVariable String race) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch average level for race: {}." : null, race);
            Double averageLevel = characteristicsService.getAverageLevelByRace(race);
            log.info(log.isInfoEnabled() ? "Average level fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(averageLevel);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch average level: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/countByType/{type}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCountByType(@PathVariable String type) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch count for type: {}." : null, type);
            Integer count = characteristicsService.getCountByType(type);
            log.info(log.isInfoEnabled() ? "Count fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(count);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch count: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/addBatch", produces = "application/json")
    public ResponseEntity<BackendResponse> addCharacteristicsBatch(@Valid @RequestBody List<CharacteristicsDTO> characteristics) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a batch of characteristics." : null);
            List<CharacteristicsDTO> addedCharacteristics = characteristicsService.insertBatch(characteristics);
            log.info(log.isInfoEnabled() ? "Characteristics batch added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedCharacteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add characteristics batch: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/updateBatch", produces = "application/json")
    public ResponseEntity<BackendResponse> updateCharacteristicsBatch(@Valid @RequestBody List<CharacteristicsDTO> characteristics) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a batch of characteristics." : null);
            List<CharacteristicsDTO> updatedCharacteristics = characteristicsService.updateBatch(characteristics);
            log.info(log.isInfoEnabled() ? "Characteristics batch updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedCharacteristics);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update characteristics batch: {}" : null, e.getMessage());
            throw e;
        }
    }
}