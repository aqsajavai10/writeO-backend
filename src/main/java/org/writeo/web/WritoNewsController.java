package org.writeo.web;

import org.writeo.dao.dto.WritoNewsDTO;
import org.writeo.service.WritoNewsService;
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
@RequestMapping("/api/v1/writonews")
@Log4j2
@AllArgsConstructor
public class WritoNewsController {

    @Autowired
    private final WritoNewsService writoNewsService;

    @GetMapping
    public ResponseEntity<BackendResponse> getAllWritoNews() {
        try {
            log.info("GET request received to fetch all WritoNews.");
            List<WritoNewsDTO> writoNewsList = writoNewsService.findAll();
            log.info("WritoNews fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(writoNewsList);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackendResponse> getWritoNewsById(@PathVariable Long id) {
        try {
            log.info("GET request received to fetch WritoNews by id: {}.", id);
            return writoNewsService.findById(id)
                    .map(writoNewsDTO -> ResponseHandlerUtil.handleSuccess(writoNewsDTO))
                    .orElse(
                           // ResponseHandlerUtil.handleResponse(writoNewsDTO, HttpStatus.NOT_FOUND)
                    null);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<BackendResponse> createWritoNews(@Valid @RequestBody WritoNewsDTO writoNewsDTO) {
        try {
            log.info("POST request received to create a WritoNews.");
            WritoNewsDTO createdWritoNews = writoNewsService.insert(writoNewsDTO);
            log.info("WritoNews created successfully.");
            return ResponseHandlerUtil.handleSuccess(createdWritoNews);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to create WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BackendResponse> updateWritoNews(@PathVariable Long id, @Valid @RequestBody WritoNewsDTO writoNewsDTO) {
        try {
            log.info("PUT request received to update WritoNews with id: {}.", id);
            writoNewsDTO.setWritonewsId(id);
            WritoNewsDTO updatedWritoNews = writoNewsService.update(writoNewsDTO);
            log.info("WritoNews updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedWritoNews);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BackendResponse> deleteWritoNews(@PathVariable Long id) {
        try {
            log.info("DELETE request received to delete WritoNews with id: {}.", id);
            writoNewsService.delete(id);
            log.info("WritoNews deleted successfully.");
            return ResponseHandlerUtil.handleSuccess("WritoNews deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to delete WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BackendResponse> searchWritoNewsByTitle(@RequestParam String title) {
        try {
            log.info("GET request received to search WritoNews by title containing: {}.", title);
            List<WritoNewsDTO> writoNewsList = writoNewsService.findByTitleContaining(title);
            log.info("WritoNews search completed successfully.");
            return ResponseHandlerUtil.handleSuccess(writoNewsList);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to search WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search/description")
    public ResponseEntity<BackendResponse> searchWritoNewsByDescription(@RequestParam String keyword) {
        try {
            log.info("GET request received to search WritoNews by description containing: {}.", keyword);
            List<WritoNewsDTO> writoNewsList = writoNewsService.findByDescriptionContaining(keyword);
            log.info("WritoNews search completed successfully.");
            return ResponseHandlerUtil.handleSuccess(writoNewsList);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to search WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<BackendResponse> getLatestWritoNews(@RequestParam(defaultValue = "5") int limit) {
        try {
            log.info("GET request received to fetch latest {} WritoNews.", limit);
            List<WritoNewsDTO> latestWritoNews = writoNewsService.findLatestNews(limit);
            log.info("Latest WritoNews fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(latestWritoNews);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch latest WritoNews: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<BackendResponse> getAllWritoNewsPaginated(Pageable pageable) {
        try {
            log.info("GET request received to fetch paginated WritoNews.");
            Page<WritoNewsDTO> writoNewsPage = writoNewsService.findAllPaginated(pageable);
            log.info("Paginated WritoNews fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(writoNewsPage);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch paginated WritoNews: {}", e.getMessage());
            throw e;
        }
    }
}