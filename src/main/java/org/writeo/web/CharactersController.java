package org.writeo.web;

import org.writeo.dao.dto.CharactersDTO;
import org.writeo.service.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;

import jakarta.validation.Valid;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;

@RestController
@RequestMapping(value = "/characters")
@Log4j2
@AllArgsConstructor
public class CharactersController {

    @Autowired
    private final CharactersService charactersService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllCharacters() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all characters." : null);
            List<CharactersDTO> characters = charactersService.findAll();
            log.info(log.isInfoEnabled() ? "Characters fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharacterById(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch character by id: {}." : null, id);
            CharactersDTO character = charactersService.findById(id);
            log.info(log.isInfoEnabled() ? "Character fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(character);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch character: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByMentionsId/{mentionsId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getCharactersByMentionsId(@PathVariable Long mentionsId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch characters by mentions id: {}." : null, mentionsId);
            List<CharactersDTO> characters = charactersService.findAllByMentionsId(mentionsId);
            log.info(log.isInfoEnabled() ? "Characters fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(characters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch characters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addCharacter(@Valid @RequestBody CharactersDTO character) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a character." : null);
            CharactersDTO addedCharacter = charactersService.insert(character);
            log.info(log.isInfoEnabled() ? "Character added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedCharacter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add character: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateCharacter(@Valid @RequestBody CharactersDTO character) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a character." : null);
            CharactersDTO updatedCharacter = charactersService.update(character);
            log.info(log.isInfoEnabled() ? "Character updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedCharacter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update character: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteCharacter(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete a character." : null);
            charactersService.delete(id);
            log.info(log.isInfoEnabled() ? "Character deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Character deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete character: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteByMentionsId/{mentionsId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteCharactersByMentionsId(@PathVariable Long mentionsId) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete characters by mentions id: {}." : null, mentionsId);
            charactersService.deleteByMentionsId(mentionsId);
            log.info(log.isInfoEnabled() ? "Characters deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Characters deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete characters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CharactersDTO>> searchCharactersByName(@RequestParam("name") String name) {
        List<CharactersDTO> characters = charactersService.findCharactersByName(name);
        if (!characters.isEmpty()) {
            return ResponseEntity.ok(characters);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CharactersDTO> createCharacter(@Valid @RequestBody CharactersDTO charactersDTO) {
        CharactersDTO createdCharacter = charactersService.createCharacter(charactersDTO);
        return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharactersDTO> updateCharacter(@PathVariable Long id, @Valid @RequestBody CharactersDTO charactersDTO) {
        CharactersDTO updatedCharacter = charactersService.updateCharacter(id, charactersDTO);
        if (updatedCharacter != null) {
            return ResponseEntity.ok(updatedCharacter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<CharactersDTO>> getAllCharactersSorted(@RequestParam String order) {
        List<CharactersDTO> sortedCharacters;
        if (order.equalsIgnoreCase("asc")) {
            sortedCharacters = charactersService.getAllCharactersSortedByNameAsc();
        } else if (order.equalsIgnoreCase("desc")) {
            sortedCharacters = charactersService.getAllCharactersSortedByNameDesc();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(sortedCharacters);
    }

    @GetMapping("/{mentionsId}/count")
    public ResponseEntity<Integer> getCharacterCountByMentionsId(@PathVariable Long mentionsId) {
        int count = charactersService.getCharacterCountByMentionsId(mentionsId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/batch")
    public ResponseEntity<BackendResponse> addCharactersBatch(@Valid @RequestBody List<CharactersDTO> characters) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a batch of characters." : null);
            List<CharactersDTO> addedCharacters = characters.stream()
                    .map(charactersService::insert)
                    .collect(Collectors.toList());
            log.info(log.isInfoEnabled() ? "Characters batch added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedCharacters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add characters batch: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/batch")
    public ResponseEntity<BackendResponse> updateCharactersBatch(@Valid @RequestBody List<CharactersDTO> characters) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a batch of characters." : null);
            List<CharactersDTO> updatedCharacters = characters.stream()
                    .map(charactersService::update)
                    .collect(Collectors.toList());
            log.info(log.isInfoEnabled() ? "Characters batch updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedCharacters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update characters batch: {}" : null, e.getMessage());
            throw e;
        }
    }

}