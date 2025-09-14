package org.writeo.service.impl;

import org.writeo.dao.mapper.CharacterRelationsMapper;
import org.writeo.dao.model.CharacterRelations;
import org.writeo.dao.repository.CharacterRelationsRepository;
import org.writeo.dao.dto.CharacterRelationsDTO;
import org.writeo.service.CharacterRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CharacterRelationsServiceImpl implements CharacterRelationsService {

    @Autowired
    private final CharacterRelationsRepository characterRelationsRepository;
    @Autowired
    private final CharacterRelationsMapper characterRelationsMapper;

    @Override
    public List<CharacterRelationsDTO> findAll() {
        log.info("Fetching all CharacterRelations");
        return characterRelationsRepository.findAll().stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterRelationsDTO> findById(Long relationId) {
        log.info("Fetching CharacterRelations by id: {}", relationId);
        return characterRelationsRepository.findById(relationId)
                .map(characterRelationsMapper::entityToDto);
    }

    @Override
    public CharacterRelationsDTO insert(CharacterRelationsDTO characterRelationsDTO) {
        log.info("Inserting new CharacterRelations: {}", characterRelationsDTO);
        CharacterRelations characterRelations = characterRelationsMapper.dtoToEntity(characterRelationsDTO);
        characterRelations = characterRelationsRepository.save(characterRelations);
        return characterRelationsMapper.entityToDto(characterRelations);
    }

    @Override
    public CharacterRelationsDTO update(CharacterRelationsDTO characterRelationsDTO) {
        log.info("Updating CharacterRelations: {}", characterRelationsDTO);
        CharacterRelations existingRelation = characterRelationsRepository.findById(characterRelationsDTO.getRelationId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characterRelationsTable));
        CharacterRelations updatedRelation = characterRelationsMapper.existingEntityToNewEntity(characterRelationsDTO, existingRelation);
        return characterRelationsMapper.entityToDto(characterRelationsRepository.save(updatedRelation));
    }

    @Override
    public void delete(Long relationId) {
        log.info("Deleting CharacterRelations with id: {}", relationId);
        characterRelationsRepository.deleteById(relationId);
    }

    @Override
    public List<CharacterRelationsDTO> findRelationsByCharacterId(Long characterId) {
        log.info("Fetching relations for character id: {}", characterId);
        return characterRelationsRepository.findByCharacterId(characterId).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterRelationsDTO> findRelationsToCharacter(Long relationToCharacterId) {
        log.info("Fetching relations to character id: {}", relationToCharacterId);
        return characterRelationsRepository.findByRelationToCharacterId(relationToCharacterId).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterRelationsDTO> findAllRelationsByCharacterId(Long characterId) {
        log.info("Fetching all relations for character id: {}", characterId);
        return characterRelationsRepository.findAllRelationsByCharacterId(characterId).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterRelationsDTO> findByRelationDescriptionContaining(String description) {
        log.info("Fetching relations with description containing: {}", description);
        return characterRelationsRepository.findByRelationDescriptionContaining(description).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllUniqueRelationDescriptions() {
        log.info("Fetching all unique relation descriptions");
        return characterRelationsRepository.findAllUniqueRelationDescriptions();
    }

    @Override
    public List<CharacterRelationsDTO> findRelationsBetweenCharacters(Long characterId1, Long characterId2) {
        log.info("Fetching relations between characters with ids: {} and {}", characterId1, characterId2);
        return characterRelationsRepository.findRelationsBetweenCharacters(characterId1, characterId2).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterRelationsDTO> findCharactersWithManyRelations(int threshold) {
        log.info("Fetching characters with more than {} relations", threshold);
        return characterRelationsRepository.findCharactersWithManyRelations(threshold).stream()
                .map(characterRelationsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int countUniqueRelationsForCharacter(Long characterId) {
        log.info("Counting unique relations for character id: {}", characterId);
        return characterRelationsRepository.countUniqueRelationsForCharacter(characterId);
    }

    @Override
    public CharacterRelationsDTO addRelationBetweenCharacters(Long characterId1, Long characterId2, String relationDescription) {
        log.info("Adding relation between characters with ids: {} and {}", characterId1, characterId2);
        CharacterRelations relation = new CharacterRelations();
        relation.setCharacterId(characterId1);
        relation.setRelationToCharacterId(characterId2);
        relation.setRelationDescription(relationDescription);
        relation = characterRelationsRepository.save(relation);
        return characterRelationsMapper.entityToDto(relation);
    }

    @Override
    public void removeRelationBetweenCharacters(Long characterId1, Long characterId2) {
        log.info("Removing relations between characters with ids: {} and {}", characterId1, characterId2);
        List<CharacterRelations> relations = characterRelationsRepository.findRelationsBetweenCharacters(characterId1, characterId2);
        characterRelationsRepository.deleteAll(relations);
    }

    @Override
    public Page<CharacterRelationsDTO> findAllPaginated(Pageable pageable) {
        log.info("Fetching paginated CharacterRelations");
        return characterRelationsRepository.findAll(pageable)
                .map(characterRelationsMapper::entityToDto);
    }
}