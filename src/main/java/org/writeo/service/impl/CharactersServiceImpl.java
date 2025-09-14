package org.writeo.service.impl;

import org.writeo.dao.mapper.CharactersMapper;
import org.writeo.dao.model.Characters;
import org.writeo.dao.repository.CharactersRepository;
import org.writeo.dao.dto.CharactersDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CharactersServiceImpl implements CharactersService {

    @Autowired
    private final CharactersRepository charactersRepository;
    @Autowired
    private final CharactersMapper charactersMapper;

    @Override
    public List<CharactersDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all Characters" : " ");
        List<Characters> charactersList = charactersRepository.findAll();
        return charactersList.stream()
                .map(charactersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public CharactersDTO findById(Long id) {
        log.info(log.isInfoEnabled() ? "Fetching Character by id: " + id : " ");
        Characters charactersEntity = charactersRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.charactersTable));
        return charactersMapper.entityToDto(charactersEntity);
    }

    @Override
    public List<CharactersDTO> findAllByMentionsId(Long mentionsId) {
        log.info(log.isInfoEnabled() ? "Fetching Characters by mentions id: " + mentionsId : " ");
        List<Characters> charactersList = charactersRepository.findAllByMentionsId(mentionsId);
        return charactersList.stream()
                .map(charactersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public CharactersDTO insert(CharactersDTO charactersDTO) {
        log.info(log.isInfoEnabled() ? "Inserting Character: " + charactersDTO : " ");
        Characters charactersEntity = charactersMapper.dtoToEntity(charactersDTO);
        charactersRepository.save(charactersEntity);
        return charactersMapper.entityToDto(charactersEntity);
    }

    @Override
    @SneakyThrows
    public CharactersDTO update(CharactersDTO charactersDTO) {
        log.info(log.isInfoEnabled() ? "Updating Character: " + charactersDTO : " ");
        Characters existingCharacter = charactersRepository.findById(charactersDTO.getCharacterId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.charactersTable));
        Characters charactersEntity = charactersMapper.existingEntityToNewEntity(charactersDTO, existingCharacter);
        return charactersMapper.entityToDto(charactersRepository.save(charactersEntity));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        log.info(log.isInfoEnabled() ? "Deleting Character with id: " + id : " ");
        Characters charactersEntity = charactersRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.charactersTable));
        charactersRepository.delete(charactersEntity);
    }

    @Override
    public void deleteByMentionsId(Long mentionsId) {
        log.info(log.isInfoEnabled() ? "Deleting Characters by mentions id: " + mentionsId : " ");
        List<Characters> charactersList = charactersRepository.findAllByMentionsId(mentionsId);
        charactersRepository.deleteAll(charactersList);
    }

    @Override
    public List<CharactersDTO> findCharactersByName(String name) {
        List<Characters> characters = charactersRepository.findByName(name);
        return characters.stream()
                .map(charactersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CharactersDTO createCharacter(CharactersDTO charactersDTO) {
        Characters character = charactersMapper.dtoToEntity(charactersDTO);
        character = charactersRepository.save(character);
        return charactersMapper.entityToDto(character);
    }

    @Override
    public CharactersDTO updateCharacter(Long id, CharactersDTO charactersDTO) {
        Characters existingCharacter = charactersRepository.findById(id).orElse(null);
        if (existingCharacter != null) {
            charactersMapper.existingEntityToNewEntity(charactersDTO, existingCharacter);
            existingCharacter = charactersRepository.save(existingCharacter);
            return charactersMapper.entityToDto(existingCharacter);
        }
        return null;
    }

    @Override
    public boolean deleteCharacter(Long id) {
        if (charactersRepository.existsById(id)) {
            charactersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CharactersDTO> getAllCharactersSortedByNameAsc() {
        List<Characters> characters = charactersRepository.findAllCharactersOrderByNameAsc();
        return characters.stream()
                .map(charactersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharactersDTO> getAllCharactersSortedByNameDesc() {
        List<Characters> characters = charactersRepository.findAllCharactersOrderByNameDesc();
        return characters.stream()
                .map(charactersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getCharacterCountByMentionsId(Long mentionsId) {
        return charactersRepository.countCharactersByMentionsId(mentionsId);
    }
}