package org.writeo.service;

import org.writeo.dao.dto.CharactersDTO;
import java.util.List;

public interface CharactersService {
    List<CharactersDTO> findAll();
    CharactersDTO findById(Long id);
    List<CharactersDTO> findAllByMentionsId(Long mentionsId);
    CharactersDTO insert(CharactersDTO charactersDTO);
    CharactersDTO update(CharactersDTO charactersDTO);
    void delete(Long id);
    void deleteByMentionsId(Long mentionsId);

    List<CharactersDTO> findCharactersByName(String name);
    CharactersDTO createCharacter(CharactersDTO charactersDTO);
    CharactersDTO updateCharacter(Long id, CharactersDTO charactersDTO);
    boolean deleteCharacter(Long id);
    List<CharactersDTO> getAllCharactersSortedByNameAsc();
    List<CharactersDTO> getAllCharactersSortedByNameDesc();
    int getCharacterCountByMentionsId(Long mentionsId);
}