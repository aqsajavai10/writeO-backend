package org.writeo.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.UserStats;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, String> {

    @Query("SELECT us FROM UserStats us WHERE us.wordCountDaily >= us.wordCountDailyGoal")
    List<UserStats> findAuthorsWhoMetDailyWordGoal();

    @Query("SELECT us FROM UserStats us WHERE us.chapterCountWeekly >= us.chapterCountWeeklyGoal")
    List<UserStats> findAuthorsWhoMetWeeklyChapterGoal();

    @Query("SELECT us FROM UserStats us WHERE us.wordPerMinute > :wpm")
    List<UserStats> findAuthorsByWordPerMinuteGreaterThan(@Param("wpm") BigDecimal wpm);

    @Query("SELECT us FROM UserStats us ORDER BY us.totalWriteTime DESC")
    List<UserStats> findTopAuthorsByTotalWriteTime(Pageable pageable);

    @Query("SELECT AVG(us.wordCountMonthly) FROM UserStats us")
    BigDecimal findAverageMonthlyWordCount();

    @Query("SELECT us FROM UserStats us WHERE us.idleTime > :threshold")
    List<UserStats> findAuthorsWithHighIdleTime(@Param("threshold") LocalTime threshold);

    @Query("SELECT us FROM UserStats us WHERE us.wordCountMonthly / us.wordCountMonthlyGoal >= :percentage")
    List<UserStats> findAuthorsNearingMonthlyGoal(@Param("percentage") BigDecimal percentage);

    @Query(value = "SELECT * FROM " + CommonConstants.userStatsTable +
            " WHERE word_count_monthly > :threshold " +
            "ORDER BY word_count_monthly DESC LIMIT :limit",
            nativeQuery = true)
    List<UserStats> findTopPerformingAuthors(@Param("threshold") BigDecimal threshold, @Param("limit") int limit);
}