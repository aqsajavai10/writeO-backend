package org.writeo.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatsDTO {

    @NotBlank(message = "Author ID must not be blank")
    @JsonProperty("author_id")
    private String authorId;

    @PositiveOrZero(message = "Word count must be zero or positive")
    @JsonProperty("word_count_daily")
    private BigDecimal wordCountDaily;

    @PositiveOrZero(message = "Word count must be zero or positive")
    @JsonProperty("word_count_weekly")
    private BigDecimal wordCountWeekly;

    @PositiveOrZero(message = "Word count must be zero or positive")
    @JsonProperty("word_count_monthly")
    private BigDecimal wordCountMonthly;

    @PositiveOrZero(message = "Word count goal must be zero or positive")
    @JsonProperty("word_count_daily_goal")
    private BigDecimal wordCountDailyGoal;

    @PositiveOrZero(message = "Word count goal must be zero or positive")
    @JsonProperty("word_count_weekly_goal")
    private BigDecimal wordCountWeeklyGoal;

    @PositiveOrZero(message = "Word count goal must be zero or positive")
    @JsonProperty("word_count_monthly_goal")
    private BigDecimal wordCountMonthlyGoal;

    @PositiveOrZero(message = "Chapter count must be zero or positive")
    @JsonProperty("chapter_count_daily")
    private BigDecimal chapterCountDaily;

    @PositiveOrZero(message = "Chapter count must be zero or positive")
    @JsonProperty("chapter_count_weekly")
    private BigDecimal chapterCountWeekly;

    @PositiveOrZero(message = "Chapter count must be zero or positive")
    @JsonProperty("chapter_count_monthly")
    private BigDecimal chapterCountMonthly;

    @PositiveOrZero(message = "Chapter count goal must be zero or positive")
    @JsonProperty("chapter_count_daily_goal")
    private BigDecimal chapterCountDailyGoal;

    @PositiveOrZero(message = "Chapter count goal must be zero or positive")
    @JsonProperty("chapter_count_weekly_goal")
    private BigDecimal chapterCountWeeklyGoal;

    @PositiveOrZero(message = "Chapter count goal must be zero or positive")
    @JsonProperty("chapter_count_monthly_goal")
    private BigDecimal chapterCountMonthlyGoal;

    @NotNull(message = "Total write time must not be null")
    @JsonProperty("total_write_time")
    private LocalTime totalWriteTime;

    @NotNull(message = "Total active time must not be null")
    @JsonProperty("total_active_time")
    private LocalTime totalActiveTime;

    @PositiveOrZero(message = "Word per minute must be zero or positive")
    @JsonProperty("word_per_minute")
    private BigDecimal wordPerMinute;

    @NotNull(message = "Idle time must not be null")
    @JsonProperty("idle_time")
    private LocalTime idleTime;

    @Size(max = 255, message = CommonConstants.maxKeyCharacter)
    private String key;
}