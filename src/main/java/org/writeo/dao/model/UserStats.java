package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.userStatsTable)
public class UserStats {

    @Id
    @Column(name = "author_id")
    private String authorId;

    @Column(name = "word_count_daily")
    private BigDecimal wordCountDaily;

    @Column(name = "word_count_weekly")
    private BigDecimal wordCountWeekly;

    @Column(name = "word_count_monthly")
    private BigDecimal wordCountMonthly;

    @Column(name = "word_count_daily_goal")
    private BigDecimal wordCountDailyGoal;

    @Column(name = "word_count_weekly_goal")
    private BigDecimal wordCountWeeklyGoal;

    @Column(name = "word_count_monthly_goal")
    private BigDecimal wordCountMonthlyGoal;

    @Column(name = "chapter_count_daily")
    private BigDecimal chapterCountDaily;

    @Column(name = "chapter_count_weekly")
    private BigDecimal chapterCountWeekly;

    @Column(name = "chapter_count_monthly")
    private BigDecimal chapterCountMonthly;

    @Column(name = "chapter_count_daily_goal")
    private BigDecimal chapterCountDailyGoal;

    @Column(name = "chapter_count_weekly_goal")
    private BigDecimal chapterCountWeeklyGoal;

    @Column(name = "chapter_count_monthly_goal")
    private BigDecimal chapterCountMonthlyGoal;

    @Column(name = "total_write_time")
    private LocalTime totalWriteTime;

    @Column(name = "total_active_time")
    private LocalTime totalActiveTime;

    @Column(name = "word_per_minute")
    private BigDecimal wordPerMinute;

    @Column(name = "idle_time")
    private LocalTime idleTime;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.userStatsTable + "_" + this.authorId;
    }
}