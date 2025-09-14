package org.writeo.web;

import org.writeo.dao.dto.ChaptersDTO;
import org.writeo.service.ChaptersService;
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
@RequestMapping(value = "/chapters")
@Log4j2
@AllArgsConstructor
public class ChaptersController {

    @Autowired
    private final ChaptersService chaptersService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllChapters() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all chapters." : null);
            List<ChaptersDTO> chapters = chaptersService.findAll();
            log.info(log.isInfoEnabled() ? "Chapters fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(chapters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch chapters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> getChapterById(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch chapter by id: {}." : null, id);
            ChaptersDTO chapter = chaptersService.findById(id);
            log.info(log.isInfoEnabled() ? "Chapter fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(chapter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch chapter: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByNovel/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getChaptersByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch chapters by novel id: {}." : null, novelId);
            List<ChaptersDTO> chapters = chaptersService.findAllByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Chapters fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(chapters);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch chapters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getLatestByNovel/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getLatestChapterByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch latest chapter by novel id: {}." : null, novelId);
            ChaptersDTO chapter = chaptersService.findLatestChapterByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Latest chapter fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(chapter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch latest chapter: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addChapter(@Valid @RequestBody ChaptersDTO chapter) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a chapter." : null);
            ChaptersDTO addedChapter = chaptersService.insert(chapter);
            log.info(log.isInfoEnabled() ? "Chapter added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedChapter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add chapter: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateChapter(@Valid @RequestBody ChaptersDTO chapter) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a chapter." : null);
            ChaptersDTO updatedChapter = chaptersService.update(chapter);
            log.info(log.isInfoEnabled() ? "Chapter updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedChapter);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update chapter: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteChapter(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete a chapter." : null);
            chaptersService.delete(id);
            log.info(log.isInfoEnabled() ? "Chapter deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Chapter deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete chapter: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteByNovel/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteChaptersByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete chapters by novel id: {}." : null, novelId);
            chaptersService.deleteByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Chapters deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Chapters deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete chapters: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getTotalWordCount/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getTotalWordCountByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch total word count for novel id: {}." : null, novelId);
            Integer totalWordCount = chaptersService.getTotalWordCountByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Total word count fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(totalWordCount);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch total word count: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getMaxChapterId", produces = "application/json")
    public ResponseEntity<BackendResponse> getMaxChapterId() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch max chapter id." : null);
            Long maxChapterId = chaptersService.getMaxChapterId();
            log.info(log.isInfoEnabled() ? "Max chapter id fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(maxChapterId);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch max chapter id: {}" : null, e.getMessage());
            throw e;
        }
    }
}