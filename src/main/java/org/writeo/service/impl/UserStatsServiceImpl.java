package org.writeo.service.impl;

import org.writeo.dao.mapper.UserStatsMapper;
import org.writeo.dao.model.UserStats;
import org.writeo.dao.repository.UserStatsRepository;
import org.writeo.dao.dto.UserStatsDTO;
import org.writeo.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class UserStatsServiceImpl implements UserStatsService {

    @Autowired
    private final UserStatsRepository userStatsRepository;
    @Autowired
    private final UserStatsMapper userStatsMapper;

    @Override
    public List<UserStatsDTO> findAll() {
        log.info("Fetching all UserStats");
        List<UserStats> userStatsList = userStatsRepository.findAll();
        return userStatsList.stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserStatsDTO> findById(String authorId) {
        log.info("Fetching UserStats for author ID: {}", authorId);
        return userStatsRepository.findById(authorId)
                .map(userStatsMapper::entityToDto);
    }

    @Override
    public UserStatsDTO insert(UserStatsDTO userStatsDTO) {
        log.info("Inserting new UserStats: {}", userStatsDTO);
        UserStats userStats = userStatsMapper.dtoToEntity(userStatsDTO);
        userStats = userStatsRepository.save(userStats);
        return userStatsMapper.entityToDto(userStats);
    }

    @Override
    public UserStatsDTO update(UserStatsDTO userStatsDTO) {
        log.info("Updating UserStats: {}", userStatsDTO);
        UserStats existingUserStats = userStatsRepository.findById(userStatsDTO.getAuthorId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        UserStats updatedUserStats = userStatsMapper.existingEntityToNewEntity(userStatsDTO, existingUserStats);
        return userStatsMapper.entityToDto(userStatsRepository.save(updatedUserStats));
    }

    @Override
    public void delete(String authorId) {
        log.info("Deleting UserStats for author ID: {}", authorId);
        userStatsRepository.deleteById(authorId);
    }

    @Override
    public List<UserStatsDTO> findAuthorsWhoMetDailyWordGoal() {
        log.info("Finding authors who met daily word goal");
        return userStatsRepository.findAuthorsWhoMetDailyWordGoal().stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatsDTO> findAuthorsWhoMetWeeklyChapterGoal() {
        log.info("Finding authors who met weekly chapter goal");
        return userStatsRepository.findAuthorsWhoMetWeeklyChapterGoal().stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatsDTO> findAuthorsByWordPerMinuteGreaterThan(BigDecimal wpm) {
        log.info("Finding authors with word per minute greater than: {}", wpm);
        return userStatsRepository.findAuthorsByWordPerMinuteGreaterThan(wpm).stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatsDTO> findTopAuthorsByTotalWriteTime(int limit) {
        log.info("Finding top {} authors by total write time", limit);
        Pageable pageable = PageRequest.of(0, limit);
        return userStatsRepository.findTopAuthorsByTotalWriteTime(pageable).stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal findAverageMonthlyWordCount() {
        log.info("Calculating average monthly word count");
        return userStatsRepository.findAverageMonthlyWordCount();
    }

    @Override
    public List<UserStatsDTO> findAuthorsWithHighIdleTime(LocalTime threshold) {
        log.info("Finding authors with idle time greater than: {}", threshold);
        return userStatsRepository.findAuthorsWithHighIdleTime(threshold).stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatsDTO> findAuthorsNearingMonthlyGoal(BigDecimal percentage) {
        log.info("Finding authors nearing monthly goal ({}% complete)", percentage);
        return userStatsRepository.findAuthorsNearingMonthlyGoal(percentage.divide(new BigDecimal(100))).stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatsDTO> findTopPerformingAuthors(BigDecimal threshold, int limit) {
        log.info("Finding top {} performing authors with monthly word count above {}", limit, threshold);
        return userStatsRepository.findTopPerformingAuthors(threshold, limit).stream()
                .map(userStatsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserStatsDTO updateDailyWordCount(String authorId, BigDecimal wordCount) {
        log.info("Updating daily word count for author ID: {} to {}", authorId, wordCount);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setWordCountDaily(wordCount);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO updateWeeklyWordCount(String authorId, BigDecimal wordCount) {
        log.info("Updating weekly word count for author ID: {} to {}", authorId, wordCount);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setWordCountWeekly(wordCount);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO updateMonthlyWordCount(String authorId, BigDecimal wordCount) {
        log.info("Updating monthly word count for author ID: {} to {}", authorId, wordCount);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setWordCountMonthly(wordCount);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO updateWordCountGoals(String authorId, BigDecimal dailyGoal, BigDecimal weeklyGoal, BigDecimal monthlyGoal) {
        log.info("Updating word count goals for author ID: {}", authorId);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setWordCountDailyGoal(dailyGoal);
        userStats.setWordCountWeeklyGoal(weeklyGoal);
        userStats.setWordCountMonthlyGoal(monthlyGoal);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO updateChapterCounts(String authorId, BigDecimal dailyCount, BigDecimal weeklyCount, BigDecimal monthlyCount) {
        log.info("Updating chapter counts for author ID: {}", authorId);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setChapterCountDaily(dailyCount);
        userStats.setChapterCountWeekly(weeklyCount);
        userStats.setChapterCountMonthly(monthlyCount);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO updateWriteTime(String authorId, LocalTime writeTime) {
        log.info("Updating write time for author ID: {} to {}", authorId, writeTime);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));
        userStats.setTotalWriteTime(writeTime);
        return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
    }

    @Override
    public UserStatsDTO calculateAndUpdateWordPerMinute(String authorId) {
        log.info("Calculating and updating word per minute for author ID: {}", authorId);
        UserStats userStats = userStatsRepository.findById(authorId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userStatsTable));

        BigDecimal totalWords = userStats.getWordCountDaily().add(userStats.getWordCountWeekly()).add(userStats.getWordCountMonthly());
        long totalMinutes = userStats.getTotalWriteTime().toSecondOfDay() / 60;

        if (totalMinutes > 0) {
            BigDecimal wpm = totalWords.divide(new BigDecimal(totalMinutes), 2, RoundingMode.HALF_UP);
            userStats.setWordPerMinute(wpm);
            return userStatsMapper.entityToDto(userStatsRepository.save(userStats));
        } else {
            log.warn("Cannot calculate word per minute for author ID: {}. Total write time is zero.", authorId);
            return userStatsMapper.entityToDto(userStats);
        }
    }

    @Override
    public Page<UserStatsDTO> findAllPaginated(Pageable pageable) {
        log.info("Fetching paginated UserStats");
        Page<UserStats> userStatsPage = userStatsRepository.findAll(pageable);
        return userStatsPage.map(userStatsMapper::entityToDto);
    }
}