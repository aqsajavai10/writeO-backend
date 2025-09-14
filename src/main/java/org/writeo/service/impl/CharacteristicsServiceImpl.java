package org.writeo.service.impl;

import org.writeo.dao.mapper.CharacteristicsMapper;
import org.writeo.dao.model.Characteristics;
import org.writeo.dao.repository.CharacteristicsRepository;
import org.writeo.dao.dto.CharacteristicsDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.CharacteristicsService;
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
public class CharacteristicsServiceImpl implements CharacteristicsService {

    @Autowired
    private final CharacteristicsRepository characteristicsRepository;
    @Autowired
    private final CharacteristicsMapper characteristicsMapper;

    @Override
    public List<CharacteristicsDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all Characteristics" : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAll();
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public CharacteristicsDTO findById(Long id) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by id: " + id : " ");
        Characteristics characteristicsEntity = characteristicsRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characteristicsTable));
        return characteristicsMapper.entityToDto(characteristicsEntity);
    }

    @Override
    public List<CharacteristicsDTO> findAllByRace(String race) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by race: " + race : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAllByRace(race);
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacteristicsDTO> findAllByType(String type) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by type: " + type : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAllByType(type);
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacteristicsDTO> findAllByLevelGreaterThanEqual(Integer minLevel) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by minimum level: " + minLevel : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAllByLevelGreaterThanEqual(minLevel);
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacteristicsDTO> findAllByEnergyGreaterThanEqual(Integer minEnergy) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by minimum energy: " + minEnergy : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAllByEnergyGreaterThanEqual(minEnergy);
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public CharacteristicsDTO insert(CharacteristicsDTO characteristicsDTO) {
        log.info(log.isInfoEnabled() ? "Inserting Characteristics: " + characteristicsDTO : " ");
        Characteristics characteristicsEntity = characteristicsMapper.dtoToEntity(characteristicsDTO);
        characteristicsRepository.save(characteristicsEntity);
        return characteristicsMapper.entityToDto(characteristicsEntity);
    }

    @Override
    @SneakyThrows
    public CharacteristicsDTO update(CharacteristicsDTO characteristicsDTO) {
        log.info(log.isInfoEnabled() ? "Updating Characteristics: " + characteristicsDTO : " ");
        Characteristics existingCharacteristics = characteristicsRepository.findById(characteristicsDTO.getCharacterId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characteristicsTable));
        Characteristics characteristicsEntity = characteristicsMapper.existingEntityToNewEntity(characteristicsDTO, existingCharacteristics);
        return characteristicsMapper.entityToDto(characteristicsRepository.save(characteristicsEntity));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        log.info(log.isInfoEnabled() ? "Deleting Characteristics with id: " + id : " ");
        Characteristics characteristicsEntity = characteristicsRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characteristicsTable));
        characteristicsRepository.delete(characteristicsEntity);
    }

    @Override
    public List<CharacteristicsDTO> findByReputationContaining(String reputation) {
        log.info(log.isInfoEnabled() ? "Fetching Characteristics by reputation containing: " + reputation : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findByReputationContaining(reputation);
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacteristicsDTO> getAllCharacteristicsSortedByLevelDesc() {
        log.info(log.isInfoEnabled() ? "Fetching all Characteristics sorted by level descending" : " ");
        List<Characteristics> characteristicsList = characteristicsRepository.findAllCharacteristicsOrderByLevelDesc();
        return characteristicsList.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageLevelByRace(String race) {
        log.info(log.isInfoEnabled() ? "Getting average level for race: " + race : " ");
        return characteristicsRepository.getAverageLevelByRace(race);
    }

    @Override
    public Integer getCountByType(String type) {
        log.info(log.isInfoEnabled() ? "Getting count for type: " + type : " ");
        return characteristicsRepository.getCountByType(type);
    }

    @Override
    public List<CharacteristicsDTO> insertBatch(List<CharacteristicsDTO> characteristicsDTOList) {
        log.info(log.isInfoEnabled() ? "Inserting batch of Characteristics" : " ");
        List<Characteristics> characteristicsEntities = characteristicsDTOList.stream()
                .map(characteristicsMapper::dtoToEntity)
                .collect(Collectors.toList());
        List<Characteristics> savedEntities = characteristicsRepository.saveAll(characteristicsEntities);
        return savedEntities.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public List<CharacteristicsDTO> updateBatch(List<CharacteristicsDTO> characteristicsDTOList) {
        log.info(log.isInfoEnabled() ? "Updating batch of Characteristics" : " ");
        List<Characteristics> updatedEntities = characteristicsDTOList.stream()
                .map(dto -> {
                    Characteristics existingCharacteristics = null;
                    try {
                        existingCharacteristics = characteristicsRepository.findById(dto.getCharacterId())
                                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.characteristicsTable));
                    } catch (CustomNoSuchRecordExistsException e) {
                        throw new RuntimeException(e);
                    }
                    return characteristicsMapper.existingEntityToNewEntity(dto, existingCharacteristics);
                })
                .collect(Collectors.toList());
        List<Characteristics> savedEntities = characteristicsRepository.saveAll(updatedEntities);
        return savedEntities.stream()
                .map(characteristicsMapper::entityToDto)
                .collect(Collectors.toList());
    }
}