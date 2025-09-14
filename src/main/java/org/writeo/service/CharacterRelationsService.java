package org.writeo.service;

import org.writeo.dao.dto.CharacterRelationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CharacterRelationsService {
    List<CharacterRelationsDTO> findAll();
    Optional<CharacterRelationsDTO> findById(Long relationId);
    CharacterRelationsDTO insert(CharacterRelationsDTO characterRelationsDTO);
    CharacterRelationsDTO update(CharacterRelationsDTO characterRelationsDTO);
    void delete(Long relationId);

    List<CharacterRelationsDTO> findRelationsByCharacterId(Long characterId);
    List<CharacterRelationsDTO> findRelationsToCharacter(Long relationToCharacterId);
    List<CharacterRelationsDTO> findAllRelationsByCharacterId(Long characterId);
    List<CharacterRelationsDTO> findByRelationDescriptionContaining(String description);
    List<String> findAllUniqueRelationDescriptions();
    List<CharacterRelationsDTO> findRelationsBetweenCharacters(Long characterId1, Long characterId2);
    List<CharacterRelationsDTO> findCharactersWithManyRelations(int threshold);
    int countUniqueRelationsForCharacter(Long characterId);

    CharacterRelationsDTO addRelationBetweenCharacters(Long characterId1, Long characterId2, String relationDescription);
    void removeRelationBetweenCharacters(Long characterId1, Long characterId2);

    Page<CharacterRelationsDTO> findAllPaginated(Pageable pageable);
}