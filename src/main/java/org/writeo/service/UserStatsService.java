package org.writeo.service;

import org.writeo.dao.dto.UserStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserStatsService {
    List<UserStatsDTO> findAll();
    Optional<UserStatsDTO> findById(String authorId);
    UserStatsDTO insert(UserStatsDTO userStatsDTO);
    UserStatsDTO update(UserStatsDTO userStatsDTO);
    void delete(String authorId);

    List<UserStatsDTO> findAuthorsWhoMetDailyWordGoal();
    List<UserStatsDTO> findAuthorsWhoMetWeeklyChapterGoal();
    List<UserStatsDTO> findAuthorsByWordPerMinuteGreaterThan(BigDecimal wpm);
    List<UserStatsDTO> findTopAuthorsByTotalWriteTime(int limit);
    BigDecimal findAverageMonthlyWordCount();
    List<UserStatsDTO> findAuthorsWithHighIdleTime(LocalTime threshold);
    List<UserStatsDTO> findAuthorsNearingMonthlyGoal(BigDecimal percentage);
    List<UserStatsDTO> findTopPerformingAuthors(BigDecimal threshold, int limit);

    UserStatsDTO updateDailyWordCount(String authorId, BigDecimal wordCount);
    UserStatsDTO updateWeeklyWordCount(String authorId, BigDecimal wordCount);
    UserStatsDTO updateMonthlyWordCount(String authorId, BigDecimal wordCount);
    UserStatsDTO updateWordCountGoals(String authorId, BigDecimal dailyGoal, BigDecimal weeklyGoal, BigDecimal monthlyGoal);
    UserStatsDTO updateChapterCounts(String authorId, BigDecimal dailyCount, BigDecimal weeklyCount, BigDecimal monthlyCount);
    UserStatsDTO updateWriteTime(String authorId, LocalTime writeTime);
    UserStatsDTO calculateAndUpdateWordPerMinute(String authorId);

    Page<UserStatsDTO> findAllPaginated(Pageable pageable);
}