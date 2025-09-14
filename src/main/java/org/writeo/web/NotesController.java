package org.writeo.web;

import org.writeo.dao.dto.NotesDTO;
import org.writeo.service.NotesService;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/notes")
@Log4j2
@AllArgsConstructor
public class NotesController {

    @Autowired
    private final NotesService notesService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllNotes() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all notes." : null);
            List<NotesDTO> notes = notesService.findAll();
            log.info(log.isInfoEnabled() ? "Notes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(notes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch notes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> getNoteById(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch note by id: {}." : null, id);
            NotesDTO note = notesService.findById(id);
            log.info(log.isInfoEnabled() ? "Note fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(note);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch note: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByChapter/{chapterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getNotesByChapterId(@PathVariable Long chapterId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch notes by chapter id: {}." : null, chapterId);
            List<NotesDTO> notes = notesService.findAllByChapterId(chapterId);
            log.info(log.isInfoEnabled() ? "Notes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(notes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch notes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByAuthor/{authorId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getNotesByAuthorId(@PathVariable String authorId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch notes by author id: {}." : null, authorId);
            List<NotesDTO> notes = notesService.findAllByAuthorId(authorId);
            log.info(log.isInfoEnabled() ? "Notes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(notes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch notes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByType/{type}", produces = "application/json")
    public ResponseEntity<BackendResponse> getNotesByType(@PathVariable String type) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch notes by type: {}." : null, type);
            List<NotesDTO> notes = notesService.findAllByType(type);
            log.info(log.isInfoEnabled() ? "Notes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(notes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch notes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addNote(@Valid @RequestBody NotesDTO note) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a note." : null);
            NotesDTO addedNote = notesService.insert(note);
            log.info(log.isInfoEnabled() ? "Note added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedNote);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add note: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateNote(@Valid @RequestBody NotesDTO note) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a note." : null);
            NotesDTO updatedNote = notesService.update(note);
            log.info(log.isInfoEnabled() ? "Note updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedNote);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update note: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteNote(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete a note." : null);
            notesService.delete(id);
            log.info(log.isInfoEnabled() ? "Note deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Note deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete note: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteByChapter/{chapterId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteNotesByChapterId(@PathVariable Long chapterId) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete notes by chapter id: {}." : null, chapterId);
            notesService.deleteByChapterId(chapterId);
            log.info(log.isInfoEnabled() ? "Notes deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Notes deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete notes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getMaxNoteId", produces = "application/json")
    public ResponseEntity<BackendResponse> getMaxNoteId() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch max note id." : null);
            Long maxNoteId = notesService.getMaxNoteId();
            log.info(log.isInfoEnabled() ? "Max note id fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(maxNoteId);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch max note id: {}" : null, e.getMessage());
            throw e;
        }
    }
}