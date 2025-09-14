package org.writeo.service;

import org.writeo.dao.dto.VolumesDTO;
import java.util.List;

public interface VolumesService {
    List<VolumesDTO> findAll();
    VolumesDTO findById(Long id);
    List<VolumesDTO> findAllByNovelId(Long novelId);
    VolumesDTO findByNovelIdAndVolumeNumber(Long novelId, Integer volumeNumber);
    VolumesDTO insert(VolumesDTO volumesDTO);
    VolumesDTO update(VolumesDTO volumesDTO);
    void delete(Long id);
    void deleteByNovelId(Long novelId);
    Integer getMaxVolumeNumberByNovelId(Long novelId);
    Long getMaxVolumeId();
    VolumesDTO createVolumeForNovel( Long novelId,VolumesDTO volumeDTO);
}
