package org.writeo.web;

import org.springframework.web.multipart.MultipartFile;
import org.writeo.dao.dto.NovelsDTO;
import org.writeo.service.NovelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
import org.writeo.utils.exceps.NovelAlreadyExistsException;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;

import jakarta.validation.Valid;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;

@RestController
@RequestMapping("/novels")
@Log4j2
@AllArgsConstructor
public class NovelsController {

    private final NovelsService novelsService;

    @GetMapping
    public ResponseEntity<BackendResponse> getAllNovels() {
        try {
            log.info("GET request received to fetch all novels.");
            List<NovelsDTO> novels = novelsService.findAll();
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to fetch novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackendResponse> getNovelById(@PathVariable Long id) {
        try {
            log.info("GET request received to fetch novel by id: {}.", id);
            NovelsDTO novel = novelsService.findById(id);
            return ResponseHandlerUtil.handleSuccess(novel);
        } catch (Exception e) {
            log.error("Failed to fetch novel: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<BackendResponse> getNovelsByAuthor(@PathVariable Long authorId) {
        try {
            log.info("GET request received to fetch novels by author id: {}.", authorId);
            List<NovelsDTO> novels = novelsService.findAllByAuthorId(authorId);
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to fetch novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<BackendResponse> getNovelsByGenre(@PathVariable String genre) {
        try {
            log.info("GET request received to fetch novels by genre: {}.", genre);
            List<NovelsDTO> novels = novelsService.findAllByGenre(genre);
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to fetch novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<BackendResponse> getNovelsByStatus(@PathVariable Character status) {
        try {
            log.info("GET request received to fetch novels by status: {}.", status);
            List<NovelsDTO> novels = novelsService.findAllByCompletionStatus(status);
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to fetch novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<BackendResponse> createNovel(@Valid @RequestPart("novel") NovelsDTO novelsDTO,
                                                       @RequestPart(value = "titlePic", required = false) MultipartFile titlePic) {
        try {
            log.info("POST request received to create a novel.");
            NovelsDTO createdNovel = novelsService.createNovel(novelsDTO);
            return ResponseHandlerUtil.handleSuccess(createdNovel);
        } catch (NovelAlreadyExistsException e) {
            log.error("Novel already exists: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        } catch (Exception e) {
            log.error("Failed to create novel: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BackendResponse> updateNovel(@PathVariable Long id, @Valid @RequestBody NovelsDTO novelsDTO) {
        try {
            log.info("PUT request received to update a novel with id: {}.", id);
            novelsDTO.setNovelId(id);
            NovelsDTO updatedNovel = novelsService.update(novelsDTO);
            return ResponseHandlerUtil.handleSuccess(updatedNovel);
        } catch (Exception e) {
            log.error("Failed to update novel: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BackendResponse> deleteNovel(@PathVariable Long id) {
        try {
            log.info("DELETE request received to delete a novel with id: {}.", id);
            novelsService.delete(id);
            return ResponseHandlerUtil.handleSuccess("Novel deleted successfully.");
        } catch (Exception e) {
            log.error("Failed to delete novel: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<BackendResponse> deleteNovelsByAuthor(@PathVariable Long authorId) {
        try {
            log.info("DELETE request received to delete novels by author id: {}.", authorId);
            novelsService.deleteByAuthorId(authorId);
            return ResponseHandlerUtil.handleSuccess("Novels deleted successfully.");
        } catch (Exception e) {
            log.error("Failed to delete novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BackendResponse> searchNovelsByName(@RequestParam("name") String name) {
        try {
            log.info("GET request received to search novels by name: {}.", name);
            List<NovelsDTO> novels = novelsService.findNovelsByName(name);
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to search novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<BackendResponse> completeNovel(@PathVariable Long id) {
        try {
            log.info("PUT request received to mark novel as completed, id: {}.", id);
            boolean isCompleted = novelsService.markNovelAsCompleted(id);
            if (isCompleted) {
                return ResponseHandlerUtil.handleSuccess("Novel has been marked as completed.");
            } else {
                return ResponseHandlerUtil.handleException(new CustomNullPointerException("Novel not found"));
            }
        } catch (Exception e) {
            log.error("Failed to mark novel as completed: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<BackendResponse> getAllNovelsSorted(@RequestParam String order) {
        try {
            log.info("GET request received to fetch all novels sorted by name in {} order.", order);
            List<NovelsDTO> novels;
            if ("asc".equalsIgnoreCase(order)) {
                novels = novelsService.getAllNovelsSortedByNameAsc();
            } else if ("desc".equalsIgnoreCase(order)) {
                novels = novelsService.getAllNovelsSortedByNameDesc();
            } else {
                throw new IllegalArgumentException("Invalid order parameter. Use 'asc' or 'desc'.");
            }
            return ResponseHandlerUtil.handleSuccess(novels);
        } catch (Exception e) {
            log.error("Failed to fetch sorted novels: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }

    @GetMapping("/{novelId}/totalVolumes")
    public ResponseEntity<BackendResponse> getTotalVolumesByNovelId(@PathVariable Long novelId) {
        try {
            log.info("GET request received to fetch total volumes for novel id: {}.", novelId);
            int totalVolumes = novelsService.getTotalVolumesByNovelId(novelId);
            return ResponseHandlerUtil.handleSuccess(totalVolumes);
        } catch (Exception e) {
            log.error("Failed to fetch total volumes: {}", e.getMessage());
            return ResponseHandlerUtil.handleException(e);
        }
    }
}