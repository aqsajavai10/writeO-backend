package org.writeo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.writeo.dao.mapper.VolumesMapper;
import org.writeo.dao.model.Volumes;
import org.writeo.dao.dto.VolumesDTO;
import org.writeo.dao.repository.VolumesRepository;
import org.writeo.service.VolumesService;

import java.util.List;
import java.util.stream.Collectors;

import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class VolumesServiceImpl implements VolumesService {

    @Autowired
    private final VolumesRepository volumesRepository;
    @Autowired
    private final VolumesMapper volumesMapper;

    @Override
    public List<VolumesDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all Volumes" : " ");
        List<Volumes> volumesList = volumesRepository.findAll();
        return volumesList.stream()
                .map(volumesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public VolumesDTO findById(Long id) {
        log.info(log.isInfoEnabled() ? "Fetching Volume by id: " + id : " ");
        Volumes volumesEntity = volumesRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.volumesTable));
        return volumesMapper.entityToDto(volumesEntity);
    }

    @Override
    public List<VolumesDTO> findAllByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Fetching Volumes by novel id: " + novelId : " ");
        List<Volumes> volumesList = volumesRepository.findAllByNovelId(novelId);
        return volumesList.stream()
                .map(volumesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VolumesDTO findByNovelIdAndVolumeNumber(Long novelId, Integer volumeNumber) {
        log.info(log.isInfoEnabled() ? "Fetching Volume by novel id: " + novelId + " and volume number: " + volumeNumber : " ");
        Volumes volumesEntity = volumesRepository.findByNovelIdAndVolumeNumber(novelId, volumeNumber);
        return volumesMapper.entityToDto(volumesEntity);
    }

    @Override
    @SneakyThrows
    public VolumesDTO insert(VolumesDTO volumesDTO) {
        log.info(log.isInfoEnabled() ? "Inserting Volume: " + volumesDTO : " ");
        Volumes volumesEntity = volumesMapper.dtoToEntity(volumesDTO);
        volumesRepository.save(volumesEntity);
        return volumesMapper.entityToDto(volumesEntity);
    }

    @Override
    @SneakyThrows
    public VolumesDTO update(VolumesDTO volumesDTO) {
        log.info(log.isInfoEnabled() ? "Updating Volume: " + volumesDTO : " ");
        Volumes existingVolume = volumesRepository.findById(volumesDTO.getVolumeId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.volumesTable));
        Volumes volumesEntity = volumesMapper.existingEntityToNewEntity(volumesDTO, existingVolume);
        return volumesMapper.entityToDto(volumesRepository.save(volumesEntity));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        log.info(log.isInfoEnabled() ? "Deleting Volume with id: " + id : " ");
        Volumes volumesEntity = volumesRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.volumesTable));
        volumesRepository.delete(volumesEntity);
    }

    @Override
    public void deleteByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Deleting Volumes by novel id: " + novelId : " ");
        List<Volumes> volumesList = volumesRepository.findAllByNovelId(novelId);
        volumesRepository.deleteAll(volumesList);
    }

    @Override
    public Integer getMaxVolumeNumberByNovelId(Long novelId) {
        return volumesRepository.findMaxVolumeNumberByNovelId(novelId);
    }

    @Override
    public Long getMaxVolumeId() {
        return volumesRepository.findMaxVolumeId();
    }

    @Override
    public VolumesDTO createVolumeForNovel(Long novelId, VolumesDTO volumeDTO) {
        return null;
    }


}
