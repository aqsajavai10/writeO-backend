package org.writeo.service.impl;

import org.writeo.dao.dto.ChaptersDTO;
import org.writeo.dao.mapper.ChaptersMapper;
import org.writeo.dao.model.Chapters;
import org.writeo.dao.repository.ChaptersRepository;
import org.writeo.service.ChaptersService;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class ChaptersServiceImpl implements ChaptersService {

    @Autowired
    private final ChaptersRepository chaptersRepository;
    @Autowired
    private final ChaptersMapper chaptersMapper;

    @Override
    public List<ChaptersDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all Chapters" : " ");
        List<Chapters> chaptersList = chaptersRepository.findAll();
        return chaptersList.stream()
                .map(chaptersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public ChaptersDTO findById(Long id) {
        log.info(log.isInfoEnabled() ? "Fetching Chapter by id: " + id : " ");
        Chapters chaptersEntity = chaptersRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.chaptersTable));
        return chaptersMapper.entityToDto(chaptersEntity);
    }

    @Override
    public List<ChaptersDTO> findAllByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Fetching Chapters by novel id: " + novelId : " ");
        List<Chapters> chaptersList = chaptersRepository.findAllByNovelId(novelId);
        return chaptersList.stream()
                .map(chaptersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChaptersDTO findLatestChapterByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Fetching latest Chapter by novel id: " + novelId : " ");
        Chapters latestChapter = chaptersRepository.findLatestChapterByNovelId(novelId);
        return chaptersMapper.entityToDto(latestChapter);
    }

    @Override
    @SneakyThrows
    public ChaptersDTO insert(ChaptersDTO chaptersDTO) {
        log.info(log.isInfoEnabled() ? "Inserting Chapter: " + chaptersDTO : " ");
        Chapters chaptersEntity = chaptersMapper.dtoToEntity(chaptersDTO);
        chaptersRepository.save(chaptersEntity);
        return chaptersMapper.entityToDto(chaptersEntity);
    }

    @Override
    @SneakyThrows
    public ChaptersDTO update(ChaptersDTO chaptersDTO) {
        log.info(log.isInfoEnabled() ? "Updating Chapter: " + chaptersDTO : " ");
        Chapters existingChapter = chaptersRepository.findById(chaptersDTO.getChId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.chaptersTable));
        Chapters chaptersEntity = chaptersMapper.existingEntityToNewEntity(chaptersDTO, existingChapter);
        return chaptersMapper.entityToDto(chaptersRepository.save(chaptersEntity));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        log.info(log.isInfoEnabled() ? "Deleting Chapter with id: " + id : " ");
        Chapters chaptersEntity = chaptersRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.chaptersTable));
        chaptersRepository.delete(chaptersEntity);
    }

    @Override
    public void deleteByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Deleting Chapters by novel id: " + novelId : " ");
        List<Chapters> chaptersList = chaptersRepository.findAllByNovelId(novelId);
        chaptersRepository.deleteAll(chaptersList);
    }

    @Override
    public Integer getTotalWordCountByNovelId(Long novelId) {
        log.info(log.isInfoEnabled() ? "Getting total word count for novel id: " + novelId : " ");
        return chaptersRepository.getTotalWordCountByNovelId(novelId);
    }

    @Override
    public Long getMaxChapterId() {
        return chaptersRepository.findMaxChapterId();
    }
}