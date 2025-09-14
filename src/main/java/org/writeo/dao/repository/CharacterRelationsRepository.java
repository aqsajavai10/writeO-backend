package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.CharacterRelations;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface CharacterRelationsRepository extends JpaRepository<CharacterRelations, Long> {

    List<CharacterRelations> findByCharacterId(Long characterId);

    List<CharacterRelations> findByRelationToCharacterId(Long relationToCharacterId);

    @Query("SELECT cr FROM CharacterRelations cr WHERE cr.characterId = :characterId OR cr.relationToCharacterId = :characterId")
    List<CharacterRelations> findAllRelationsByCharacterId(@Param("characterId") Long characterId);

    @Query("SELECT cr FROM CharacterRelations cr WHERE cr.relationDescription LIKE %:description%")
    List<CharacterRelations> findByRelationDescriptionContaining(@Param("description") String description);

    @Query("SELECT DISTINCT cr.relationDescription FROM CharacterRelations cr")
    List<String> findAllUniqueRelationDescriptions();

    @Query("SELECT cr FROM CharacterRelations cr WHERE cr.characterId = :characterId1 AND cr.relationToCharacterId = :characterId2")
    List<CharacterRelations> findRelationsBetweenCharacters(@Param("characterId1") Long characterId1, @Param("characterId2") Long characterId2);

    @Query(value = "SELECT * FROM " + CommonConstants.characterRelationsTable +
            " WHERE character_id IN (SELECT character_id FROM " + CommonConstants.characterRelationsTable +
            " GROUP BY character_id HAVING COUNT(DISTINCT relation_to_character_id) > :threshold)",
            nativeQuery = true)
    List<CharacterRelations> findCharactersWithManyRelations(@Param("threshold") int threshold);

    @Query("SELECT COUNT(DISTINCT cr.relationToCharacterId) FROM CharacterRelations cr WHERE cr.characterId = :characterId")
    int countUniqueRelationsForCharacter(@Param("characterId") Long characterId);
}