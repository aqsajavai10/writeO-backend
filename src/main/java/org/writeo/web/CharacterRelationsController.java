package org.writeo.web;

import org.writeo.dao.dto.CharacterRelationsDTO;
import org.writeo.service.CharacterRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/character-relations")
@Log4j2
@AllArgsConstructor
public class CharacterRelationsController {

    @Autowired
    private final CharacterRelationsService characterRelationsService;

    @GetMapping
    public ResponseEntity<BackendResponse> getAllCharacterRelations(Pageable pageable) {
        try {
            log.info("GET request received to fetch all CharacterRelations.");
            Page<CharacterRelationsDTO> characterRelations = characterRelationsService.findAllPaginated(pageable);
            log.info("CharacterRelations fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characterRelations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch CharacterRelations: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{relationId}")
    public ResponseEntity<BackendResponse> getCharacterRelationById(@PathVariable Long relationId) {
        try {
            log.info("GET request received to fetch CharacterRelation by id: {}.", relationId);
            return characterRelationsService.findById(relationId)
                    .map(ResponseHandlerUtil::handleSuccess)
                    .orElse(ResponseHandlerUtil.handleError("CharacterRelation not found", HttpStatus.NOT_FOUND));
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch CharacterRelation: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<BackendResponse> createCharacterRelation(@Valid @RequestBody CharacterRelationsDTO characterRelationsDTO) {
        try {
            log.info("POST request received to create a CharacterRelation.");
            CharacterRelationsDTO createdRelation = characterRelationsService.insert(characterRelationsDTO);
            log.info("CharacterRelation created successfully.");
            return ResponseHandlerUtil.handleSuccess(createdRelation);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to create CharacterRelation: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{relationId}")
    public ResponseEntity<BackendResponse> updateCharacterRelation(@PathVariable Long relationId, @Valid @RequestBody CharacterRelationsDTO characterRelationsDTO) {
        try {
            log.info("PUT request received to update CharacterRelation with id: {}.", relationId);
            characterRelationsDTO.setRelationId(relationId);
            CharacterRelationsDTO updatedRelation = characterRelationsService.update(characterRelationsDTO);
            log.info("CharacterRelation updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedRelation);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update CharacterRelation: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{relationId}")
    public ResponseEntity<BackendResponse> deleteCharacterRelation(@PathVariable Long relationId) {
        try {
            log.info("DELETE request received to delete CharacterRelation with id: {}.", relationId);
            characterRelationsService.delete(relationId);
            log.info("CharacterRelation deleted successfully.");
            return ResponseHandlerUtil.handleSuccess("CharacterRelation deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to delete CharacterRelation: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<BackendResponse> getRelationsByCharacterId(@PathVariable Long characterId) {
        try {
            log.info("GET request received to fetch relations for character id: {}.", characterId);
            List<CharacterRelationsDTO> relations = characterRelationsService.findRelationsByCharacterId(characterId);
            log.info("Relations fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(relations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch relations: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/all-relations/{characterId}")
    public ResponseEntity<BackendResponse> getAllRelationsByCharacterId(@PathVariable Long characterId) {
        try {
            log.info("GET request received to fetch all relations for character id: {}.", characterId);
            List<CharacterRelationsDTO> relations = characterRelationsService.findAllRelationsByCharacterId(characterId);
            log.info("All relations fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(relations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch all relations: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BackendResponse> searchRelationsByDescription(@RequestParam String description) {
        try {
            log.info("GET request received to search relations by description: {}.", description);
            List<CharacterRelationsDTO> relations = characterRelationsService.findByRelationDescriptionContaining(description);
            log.info("Relations searched successfully.");
            return ResponseHandlerUtil.handleSuccess(relations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to search relations: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/unique-descriptions")
    public ResponseEntity<BackendResponse> getUniqueRelationDescriptions() {
        try {
            log.info("GET request received to fetch unique relation descriptions.");
            List<String> descriptions = characterRelationsService.findAllUniqueRelationDescriptions();
            log.info("Unique relation descriptions fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(descriptions);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch unique relation descriptions: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/between")
    public ResponseEntity<BackendResponse> getRelationsBetweenCharacters(
            @RequestParam Long characterId1,
            @RequestParam Long characterId2) {
        try {
            log.info("GET request received to fetch relations between characters {} and {}.", characterId1, characterId2);
            List<CharacterRelationsDTO> relations = characterRelationsService.findRelationsBetweenCharacters(characterId1, characterId2);
            log.info("Relations between characters fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(relations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch relations between characters: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/many-relations")
    public ResponseEntity<BackendResponse> getCharactersWithManyRelations(@RequestParam(defaultValue = "5") int threshold) {
        try {
            log.info("GET request received to fetch characters with more than {} relations.", threshold);
            List<CharacterRelationsDTO> relations = characterRelationsService.findCharactersWithManyRelations(threshold);
            log.info("Characters with many relations fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(relations);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch characters with many relations: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/count/{characterId}")
    public ResponseEntity<BackendResponse> countUniqueRelationsForCharacter(@PathVariable Long characterId) {
        try {
            log.info("GET request received to count unique relations for character {}.", characterId);
            int count = characterRelationsService.countUniqueRelationsForCharacter(characterId);
            log.info("Unique relations counted successfully.");
            return ResponseHandlerUtil.handleSuccess(count);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to count unique relations: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/add-relation")
    public ResponseEntity<BackendResponse> addRelationBetweenCharacters(
            @RequestParam Long characterId1,
            @RequestParam Long characterId2,
            @RequestParam String relationDescription) {
        try {
            log.info("POST request received to add relation between characters {} and {}.", characterId1, characterId2);
            CharacterRelationsDTO relation = characterRelationsService.addRelationBetweenCharacters(characterId1, characterId2, relationDescription);
            log.info("Relation added successfully.");
            return ResponseHandlerUtil.handleSuccess(relation);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to add relation between characters: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/remove-relation")
    public ResponseEntity<BackendResponse> removeRelationBetweenCharacters(
            @RequestParam Long characterId1,
            @RequestParam Long characterId2) {
        try {
            log.info("DELETE request received to remove relations between characters {} and {}.", characterId1, characterId2);
            characterRelationsService.removeRelationBetweenCharacters(characterId1, characterId2);
            log.info("Relations removed successfully.");
            return ResponseHandlerUtil.handleSuccess("Relations between characters removed successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to remove relations between characters: {}", e.getMessage());
            throw e;
        }
    }
}