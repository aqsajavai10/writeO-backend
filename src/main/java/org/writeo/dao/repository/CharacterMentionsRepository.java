package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.CharacterMentions;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterMentionsRepository extends JpaRepository<CharacterMentions, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.characterMentionsTable +
            " WHERE character_id = :characterId AND chapter_id = :chapterId", nativeQuery = true)
    Optional<CharacterMentions> findById(@Param("characterId") Long characterId, @Param("chapterId") Long chapterId);

    @Query(value = "SELECT * FROM " + CommonConstants.characterMentionsTable +
            " WHERE character_id = :characterId AND mention_id = :mentionId AND chapter_id = :chapterId", nativeQuery = true)
    Optional<CharacterMentions> findById(@Param("characterId") Long characterId, @Param("mentionId") Long mentionId, @Param("chapterId") Long chapterId);

    @Query(value = "SELECT * FROM " + CommonConstants.characterMentionsTable + " WHERE character_id = :characterId", nativeQuery = true)
    List<CharacterMentions> findAllByCharacterId(@Param("characterId") Long characterId);

    @Query(value = "SELECT * FROM " + CommonConstants.characterMentionsTable + " WHERE chapter_id = :chapterId", nativeQuery = true)
    List<CharacterMentions> findAllByChapterId(@Param("chapterId") Long chapterId);

    @Query(value = "SELECT DISTINCT character_id FROM " + CommonConstants.characterMentionsTable +
            " WHERE chapter_id = :chapterId", nativeQuery = true)
    List<Long> findDistinctCharactersByChapterId(@Param("chapterId") Long chapterId);

    @Query(value = "SELECT COUNT(*) FROM " + CommonConstants.characterMentionsTable +
            " WHERE character_id = :characterId", nativeQuery = true)
    int countMentionsByCharacterId(@Param("characterId") Long characterId);

    @Query(value = "SELECT chapter_id FROM " + CommonConstants.characterMentionsTable +
            " WHERE character_id = :characterId GROUP BY chapter_id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Long findMostMentionedChapterForCharacter(@Param("characterId") Long characterId);

    @Query(value = "SELECT * FROM " + CommonConstants.characterMentionsTable +
            " WHERE (character_id = :character1Id AND mention_id = :character2Id) OR " +
            "(character_id = :character2Id AND mention_id = :character1Id)", nativeQuery = true)
    List<CharacterMentions> findCharacterInteractions(@Param("character1Id") Long character1Id, @Param("character2Id") Long character2Id);
}