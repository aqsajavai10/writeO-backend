package org.writeo.service.impl;

import org.writeo.dao.mapper.CharacterMentionsMapper;
import org.writeo.dao.model.CharacterMentions;
import org.writeo.dao.repository.CharacterMentionsRepository;
import org.writeo.dao.dto.CharacterMentionsDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.CharacterMentionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CharacterMentionsServiceImpl implements CharacterMentionsService {

    @Autowired
    private final CharacterMentionsRepository characterMentionsRepository;
    @Autowired
    private final CharacterMentionsMapper characterMentionsMapper;

    @Override
    public List<CharacterMentionsDTO> findAll() {
        log.info("Fetching all CharacterMentions");
        List<CharacterMentions> characterMentionsList = characterMentionsRepository.findAll();
        return characterMentionsList.stream()
                .map(characterMentionsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterMentionsDTO findById(Long characterId, Long chapterId) throws CustomNoSuchRecordExistsException {
        log.info("Fetching CharacterMention by ids: character={}, chapter={}", characterId, chapterId);
        CharacterMentions characterMentionsEntity = characterMentionsRepository.findById(characterId, chapterId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characterMentionsTable));
        return characterMentionsMapper.entityToDto(characterMentionsEntity);
    }

    @Override
    public CharacterMentionsDTO insert(CharacterMentionsDTO characterMentionsDTO) {
        log.info("Inserting CharacterMention: {}", characterMentionsDTO);
        CharacterMentions characterMentionsEntity = characterMentionsMapper.dtoToEntity(characterMentionsDTO);
        characterMentionsRepository.save(characterMentionsEntity);
        return characterMentionsMapper.entityToDto(characterMentionsEntity);
    }

    @Override
    public CharacterMentionsDTO update(CharacterMentionsDTO characterMentionsDTO) throws CustomNoSuchRecordExistsException {
        log.info("Updating CharacterMention: {}", characterMentionsDTO);
        CharacterMentions existingMention = characterMentionsRepository.findById(
                characterMentionsDTO.getCharacterId(),
                characterMentionsDTO.getChapterId()
        ).orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characterMentionsTable));
        CharacterMentions characterMentionsEntity = characterMentionsMapper.dtoToEntity(characterMentionsDTO);
        return characterMentionsMapper.entityToDto(characterMentionsRepository.save(characterMentionsEntity));
    }

    @Override
    public void delete(Long characterId, Long chapterId) throws CustomNoSuchRecordExistsException {
        log.info("Deleting CharacterMention with ids: character={}, chapter={}", characterId, chapterId);
        CharacterMentions characterMentionsEntity = characterMentionsRepository.findById(characterId, chapterId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characterMentionsTable));
        characterMentionsRepository.delete(characterMentionsEntity);
    }

    @Override
    public List<CharacterMentionsDTO> findByCharacterId(Long characterId) {
        log.info("Fetching CharacterMentions by character id: {}", characterId);
        List<CharacterMentions> characterMentionsList = characterMentionsRepository.findAllByCharacterId(characterId);
        return characterMentionsList.stream()
                .map(characterMentionsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterMentionsDTO> findByChapterId(Long chapterId) {
        log.info("Fetching CharacterMentions by chapter id: {}", chapterId);
        List<CharacterMentions> characterMentionsList = characterMentionsRepository.findAllByChapterId(chapterId);
        return characterMentionsList.stream()
                .map(characterMentionsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findCharactersInChapter(Long chapterId) {
        log.info("Finding characters in chapter: {}", chapterId);
        return characterMentionsRepository.findDistinctCharactersByChapterId(chapterId);
    }

    @Override
    public int countMentionsForCharacter(Long characterId) {
        log.info("Counting mentions for character: {}", characterId);
        return characterMentionsRepository.countMentionsByCharacterId(characterId);
    }

    @Override
    public Long findMostMentionedChapterForCharacter(Long characterId) {
        log.info("Finding most mentioned chapter for character: {}", characterId);
        return characterMentionsRepository.findMostMentionedChapterForCharacter(characterId);
    }

    @Override
    public List<CharacterMentionsDTO> findCharacterInteractions(Long character1Id, Long character2Id) {
        log.info("Finding interactions between characters: {} and {}", character1Id, character2Id);
        List<CharacterMentions> interactions = characterMentionsRepository.findCharacterInteractions(character1Id, character2Id);
        return interactions.stream()
                .map(characterMentionsMapper::entityToDto)
                .collect(Collectors.toList());
    }
}