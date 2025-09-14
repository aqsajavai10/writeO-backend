package org.writeo.web;

import org.writeo.dao.dto.UserStatsDTO;
import org.writeo.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userstats")
@Log4j2
@AllArgsConstructor
public class UserStatsController {

    @Autowired
    private final UserStatsService userStatsService;

    @GetMapping
    public ResponseEntity<BackendResponse> getAllUserStats(Pageable pageable) {
        try {
            log.info("GET request received to fetch all UserStats.");
            Page<UserStatsDTO> userStats = userStatsService.findAllPaginated(pageable);
            log.info("UserStats fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(userStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch UserStats: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<BackendResponse> getUserStatsById(@PathVariable String authorId) {
        try {
            log.info("GET request received to fetch UserStats for author ID: {}.", authorId);
            return userStatsService.findById(authorId)
                    .map(userStatsDTO -> ResponseHandlerUtil.handleSuccess(userStatsDTO))
                    .orElse(ResponseHandlerUtil.handleError("UserStats not found", HttpStatus.NOT_FOUND));
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch UserStats: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<BackendResponse> createUserStats(@Valid @RequestBody UserStatsDTO userStatsDTO) {
        try {
            log.info("POST request received to create UserStats.");
            UserStatsDTO createdUserStats = userStatsService.insert(userStatsDTO);
            log.info("UserStats created successfully.");
            return ResponseHandlerUtil.handleSuccess(createdUserStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to create UserStats: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<BackendResponse> updateUserStats(@PathVariable String authorId, @Valid @RequestBody UserStatsDTO userStatsDTO) {
        try {
            log.info("PUT request received to update UserStats for author ID: {}.", authorId);
            userStatsDTO.setAuthorId(authorId);
            UserStatsDTO updatedUserStats = userStatsService.update(userStatsDTO);
            log.info("UserStats updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedUserStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update UserStats: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<BackendResponse> deleteUserStats(@PathVariable String authorId) {
        try {
            log.info("DELETE request received to delete UserStats for author ID: {}.", authorId);
            userStatsService.delete(authorId);
            log.info("UserStats deleted successfully.");
            return ResponseHandlerUtil.handleSuccess("UserStats deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to delete UserStats: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/daily-goal-achieved")
    public ResponseEntity<BackendResponse> getAuthorsWhoMetDailyWordGoal() {
        try {
            log.info("GET request received to fetch authors who met daily word goal.");
            List<UserStatsDTO> authors = userStatsService.findAuthorsWhoMetDailyWordGoal();
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/weekly-chapter-goal-achieved")
    public ResponseEntity<BackendResponse> getAuthorsWhoMetWeeklyChapterGoal() {
        try {
            log.info("GET request received to fetch authors who met weekly chapter goal.");
            List<UserStatsDTO> authors = userStatsService.findAuthorsWhoMetWeeklyChapterGoal();
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/high-wpm")
    public ResponseEntity<BackendResponse> getAuthorsByHighWordPerMinute(@RequestParam BigDecimal wpm) {
        try {
            log.info("GET request received to fetch authors with word per minute greater than: {}.", wpm);
            List<UserStatsDTO> authors = userStatsService.findAuthorsByWordPerMinuteGreaterThan(wpm);
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/top-write-time")
    public ResponseEntity<BackendResponse> getTopAuthorsByWriteTime(@RequestParam(defaultValue = "10") int limit) {
        try {
            log.info("GET request received to fetch top {} authors by write time.", limit);
            List<UserStatsDTO> authors = userStatsService.findTopAuthorsByTotalWriteTime(limit);
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/average-monthly-word-count")
    public ResponseEntity<BackendResponse> getAverageMonthlyWordCount() {
        try {
            log.info("GET request received to fetch average monthly word count.");
            BigDecimal averageCount = userStatsService.findAverageMonthlyWordCount();
            log.info("Average monthly word count calculated successfully.");
            return ResponseHandlerUtil.handleSuccess(averageCount);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to calculate average monthly word count: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/high-idle-time")
    public ResponseEntity<BackendResponse> getAuthorsWithHighIdleTime(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime threshold) {
        try {
            log.info("GET request received to fetch authors with high idle time (threshold: {}).", threshold);
            List<UserStatsDTO> authors = userStatsService.findAuthorsWithHighIdleTime(threshold);
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/nearing-monthly-goal")
    public ResponseEntity<BackendResponse> getAuthorsNearingMonthlyGoal(@RequestParam BigDecimal percentage) {
        try {
            log.info("GET request received to fetch authors nearing monthly goal ({}% complete).", percentage);
            List<UserStatsDTO> authors = userStatsService.findAuthorsNearingMonthlyGoal(percentage);
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/top-performing")
    public ResponseEntity<BackendResponse> getTopPerformingAuthors(
            @RequestParam BigDecimal threshold,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            log.info("GET request received to fetch top {} performing authors with monthly word count above {}.", limit, threshold);
            List<UserStatsDTO> authors = userStatsService.findTopPerformingAuthors(threshold, limit);
            log.info("Authors fetched successfully.");
            return ResponseHandlerUtil.handleSuccess(authors);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to fetch authors: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}/update-word-count")
    public ResponseEntity<BackendResponse> updateWordCount(
            @PathVariable String authorId,
            @RequestParam String period,
            @RequestParam BigDecimal wordCount) {
        try {
            log.info("PUT request received to update {} word count for author ID: {}.", period, authorId);
            UserStatsDTO updatedStats;
            switch (period.toLowerCase()) {
                case "daily":
                    updatedStats = userStatsService.updateDailyWordCount(authorId, wordCount);
                    break;
                case "weekly":
                    updatedStats = userStatsService.updateWeeklyWordCount(authorId, wordCount);
                    break;
                case "monthly":
                    updatedStats = userStatsService.updateMonthlyWordCount(authorId, wordCount);
                    break;
                default:
                    return ResponseHandlerUtil.handleError("Invalid period specified", HttpStatus.BAD_REQUEST);
            }
            log.info("Word count updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update word count: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}/update-word-count-goals")
    public ResponseEntity<BackendResponse> updateWordCountGoals(
            @PathVariable String authorId,
            @RequestParam BigDecimal dailyGoal,
            @RequestParam BigDecimal weeklyGoal,
            @RequestParam BigDecimal monthlyGoal) {
        try {
            log.info("PUT request received to update word count goals for author ID: {}.", authorId);
            UserStatsDTO updatedStats = userStatsService.updateWordCountGoals(authorId, dailyGoal, weeklyGoal, monthlyGoal);
            log.info("Word count goals updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update word count goals: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}/update-chapter-counts")
    public ResponseEntity<BackendResponse> updateChapterCounts(
            @PathVariable String authorId,
            @RequestParam BigDecimal dailyCount,
            @RequestParam BigDecimal weeklyCount,
            @RequestParam BigDecimal monthlyCount) {
        try {
            log.info("PUT request received to update chapter counts for author ID: {}.", authorId);
            UserStatsDTO updatedStats = userStatsService.updateChapterCounts(authorId, dailyCount, weeklyCount, monthlyCount);
            log.info("Chapter counts updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update chapter counts: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}/update-write-time")
    public ResponseEntity<BackendResponse> updateWriteTime(
            @PathVariable String authorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime writeTime) {
        try {
            log.info("PUT request received to update write time for author ID: {}.", authorId);
            UserStatsDTO updatedStats = userStatsService.updateWriteTime(authorId, writeTime);
            log.info("Write time updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to update write time: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{authorId}/calculate-wpm")
    public ResponseEntity<BackendResponse> calculateAndUpdateWordPerMinute(@PathVariable String authorId) {
        try {
            log.info("PUT request received to calculate and update word per minute for author ID: {}.", authorId);
            UserStatsDTO updatedStats = userStatsService.calculateAndUpdateWordPerMinute(authorId);
            log.info("Word per minute calculated and updated successfully.");
            return ResponseHandlerUtil.handleSuccess(updatedStats);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn("Failed to calculate and update word per minute: {}", e.getMessage());
            throw e;
        }
    }
}