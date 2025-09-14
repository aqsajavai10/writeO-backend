package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.Characteristics;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface CharacteristicsRepository extends JpaRepository<Characteristics, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.characteristicsTable + " WHERE race = :race", nativeQuery = true)
    List<Characteristics> findAllByRace(String race);

    @Query(value = "SELECT * FROM " + CommonConstants.characteristicsTable + " WHERE type = :type", nativeQuery = true)
    List<Characteristics> findAllByType(String type);

    @Query(value = "SELECT * FROM " + CommonConstants.characteristicsTable + " WHERE level >= :minLevel", nativeQuery = true)
    List<Characteristics> findAllByLevelGreaterThanEqual(Integer minLevel);

    @Query(value = "SELECT * FROM " + CommonConstants.characteristicsTable + " WHERE energy >= :minEnergy", nativeQuery = true)
    List<Characteristics> findAllByEnergyGreaterThanEqual(Integer minEnergy);

    @Query("SELECT c FROM Characteristics c WHERE LOWER(c.reputation) LIKE LOWER(CONCAT('%', :reputation, '%'))")
    List<Characteristics> findByReputationContaining(@Param("reputation") String reputation);

    @Query("SELECT c FROM Characteristics c ORDER BY c.level DESC")
    List<Characteristics> findAllCharacteristicsOrderByLevelDesc();

    @Query("SELECT AVG(c.level) FROM Characteristics c WHERE c.race = :race")
    Double getAverageLevelByRace(@Param("race") String race);

    @Query("SELECT COUNT(c) FROM Characteristics c WHERE c.type = :type")
    Integer getCountByType(@Param("type") String type);
}