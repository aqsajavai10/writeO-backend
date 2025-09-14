package org.writeo.web;

import org.writeo.dao.dto.CharacterMentionsDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.CharacterMentionsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/character-mentions")
@Log4j2
@AllArgsConstructor
public class CharacterMentionsController {

    @Autowired
    private final CharacterMentionsService characterMentionsService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<BackendResponse> getAllCharacterMentions() {
        try {
            log.info("GET request received to fetch all character mentions.");
            List<CharacterMentionsDTO> characterMentions = characterMentionsService.findAll();
            log.info("Character mentions fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characterMentions);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch character mentions: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/{characterId}/{chapterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacterMentionById(
            @PathVariable Long characterId,
            @PathVariable Long chapterId) throws CustomNoSuchRecordExistsException {
        try {
            log.info("GET request received to fetch character mention by ids: character={}, chapter={}", characterId, chapterId);
            CharacterMentionsDTO characterMention = characterMentionsService.findById(characterId, chapterId);
            log.info("Character mention fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characterMention);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException |
                 CustomNoSuchRecordExistsException e) {
            log.warn("Failed to fetch character mention: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<BackendResponse> addCharacterMention(@Valid @RequestBody CharacterMentionsDTO characterMention) {
        try {
            log.info("POST request received to add a character mention.");
            CharacterMentionsDTO addedMention = characterMentionsService.insert(characterMention);
            log.info("Character mention added successfully.");
            return ResponseHandlerUtil.handleSuccess(addedMention);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to add character mention: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<BackendResponse> updateCharacterMention(@Valid @RequestBody CharacterMentionsDTO characterMention) throws CustomNoSuchRecordExistsException {
        try {
            log.info("PUT request received to update a character mention.");
            CharacterMentionsDTO updatedMention = characterMentionsService.update(characterMention);
            log.info("Character mention updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedMention);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException |
                 CustomNoSuchRecordExistsException e) {
            log.warn("Failed to update character mention: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{characterId}/{chapterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteCharacterMention(
            @PathVariable Long characterId,
            @PathVariable Long chapterId) {
        try {
            log.info("DELETE request received to delete a character mention.");
            characterMentionsService.delete(characterId, chapterId);
            log.info("Character mention deleted successfully.");
            return ResponseHandlerUtil.handleSuccess("Character mention deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to delete character mention: {}", e.getMessage());
            throw e;
        } catch (CustomNoSuchRecordExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/character/{characterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacterMentionsByCharacterId(@PathVariable Long characterId) {
        try {
            log.info("GET request received to fetch character mentions by character id: {}", characterId);
            List<CharacterMentionsDTO> characterMentions = characterMentionsService.findByCharacterId(characterId);
            log.info("Character mentions fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characterMentions);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch character mentions: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/chapter/{chapterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacterMentionsByChapterId(@PathVariable Long chapterId) {
        try {
            log.info("GET request received to fetch character mentions by chapter id: {}", chapterId);
            List<CharacterMentionsDTO> characterMentions = characterMentionsService.findByChapterId(chapterId);
            log.info("Character mentions fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characterMentions);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch character mentions: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/chapter/{chapterId}/characters", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharactersInChapter(@PathVariable Long chapterId) {
        try {
            log.info("GET request received to fetch characters in chapter: {}", chapterId);
            List<Long> characters = characterMentionsService.findCharactersInChapter(chapterId);
            log.info("Characters in chapter fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(characters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch characters in chapter: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/character/{characterId}/count", produces = "application/json")
    public ResponseEntity<BackendResponse> countMentionsForCharacter(@PathVariable Long characterId) {
        try {
            log.info("GET request received to count mentions for character: {}", characterId);
            int count = characterMentionsService.countMentionsForCharacter(characterId);
            log.info("Mentions counted successfully.");
            return ResponseHandlerUtil.handleSuccess(count);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to count mentions for character: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/character/{characterId}/most-mentioned-chapter", produces = "application/json")
    public ResponseEntity<BackendResponse> getMostMentionedChapterForCharacter(@PathVariable Long characterId) {
        try {
            log.info("GET request received to find most mentioned chapter for character: {}", characterId);
            Long chapterId = characterMentionsService.findMostMentionedChapterForCharacter(characterId);
            log.info("Most mentioned chapter found successfully.");
            return ResponseHandlerUtil.handleSuccess(chapterId);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to find most mentioned chapter for character: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/interactions", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacterInteractions(
            @RequestParam Long character1Id,
            @RequestParam Long character2Id) {
        try {
            log.info("GET request received to find interactions between characters: {} and {}", character1Id, character2Id);
            List<CharacterMentionsDTO> interactions = characterMentionsService.findCharacterInteractions(character1Id, character2Id);
            log.info("Character interactions found successfully.");
            return ResponseHandlerUtil.handleSuccess(interactions);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to find character interactions: {}", e.getMessage());
            throw e;
        }
    }
}