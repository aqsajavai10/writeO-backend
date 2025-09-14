package org.writeo.service;

import org.writeo.dao.dto.CharacterMentionsDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;

import java.util.List;

public interface CharacterMentionsService {
    List<CharacterMentionsDTO> findAll();
    CharacterMentionsDTO findById(Long characterId, Long chapterId) throws CustomNoSuchRecordExistsException;
    CharacterMentionsDTO insert(CharacterMentionsDTO characterMentionsDTO);
    CharacterMentionsDTO update(CharacterMentionsDTO characterMentionsDTO) throws CustomNoSuchRecordExistsException;
    void delete(Long characterId, Long chapterId) throws CustomNoSuchRecordExistsException;

    List<CharacterMentionsDTO> findByCharacterId(Long characterId);
    List<CharacterMentionsDTO> findByChapterId(Long chapterId);
    List<Long> findCharactersInChapter(Long chapterId);
    int countMentionsForCharacter(Long characterId);
    Long findMostMentionedChapterForCharacter(Long characterId);
    List<CharacterMentionsDTO> findCharacterInteractions(Long character1Id, Long character2Id);
}