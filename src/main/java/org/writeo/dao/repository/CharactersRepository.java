package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.Characters;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.charactersTable + " WHERE mentions_id = :mentionsId", nativeQuery = true)
    List<Characters> findAllByMentionsId(Long mentionsId);

    @Query(value = "SELECT MAX(character_id) FROM " + CommonConstants.charactersTable, nativeQuery = true)
    Long findMaxCharacterId();

    @Query("SELECT c FROM Characters c WHERE LOWER(REPLACE(c.characterName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))")
    List<Characters> findByName(@Param("name") String name);

    @Query("SELECT c FROM Characters c ORDER BY c.characterName ASC")
    List<Characters> findAllCharactersOrderByNameAsc();

    @Query("SELECT c FROM Characters c ORDER BY c.characterName DESC")
    List<Characters> findAllCharactersOrderByNameDesc();

    boolean existsByCharacterName(String characterName);

    @Query("SELECT COUNT(c) FROM Characters c WHERE c.mentionsId = :mentionsId")
    int countCharactersByMentionsId(Long mentionsId);
}