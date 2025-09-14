package org.writeo.service;

import org.writeo.dao.dto.CharacteristicsDTO;
import java.util.List;

public interface CharacteristicsService {
    List<CharacteristicsDTO> findAll();
    CharacteristicsDTO findById(Long id);
    List<CharacteristicsDTO> findAllByRace(String race);
    List<CharacteristicsDTO> findAllByType(String type);
    List<CharacteristicsDTO> findAllByLevelGreaterThanEqual(Integer minLevel);
    List<CharacteristicsDTO> findAllByEnergyGreaterThanEqual(Integer minEnergy);
    CharacteristicsDTO insert(CharacteristicsDTO characteristicsDTO);
    CharacteristicsDTO update(CharacteristicsDTO characteristicsDTO);
    void delete(Long id);
    List<CharacteristicsDTO> findByReputationContaining(String reputation);
    List<CharacteristicsDTO> getAllCharacteristicsSortedByLevelDesc();
    Double getAverageLevelByRace(String race);
    Integer getCountByType(String type);
    List<CharacteristicsDTO> insertBatch(List<CharacteristicsDTO> characteristicsDTOList);
    List<CharacteristicsDTO> updateBatch(List<CharacteristicsDTO> characteristicsDTOList);
}