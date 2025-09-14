package org.writeo.service.impl;

import org.writeo.dao.mapper.WritoNewsMapper;
import org.writeo.dao.model.WritoNews;
import org.writeo.dao.repository.WritoNewsRepository;
import org.writeo.dao.dto.WritoNewsDTO;
import org.writeo.service.WritoNewsService;
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
public class WritoNewsServiceImpl implements WritoNewsService {

    @Autowired
    private final WritoNewsRepository writoNewsRepository;
    @Autowired
    private final WritoNewsMapper writoNewsMapper;

    @Override
    public List<WritoNewsDTO> findAll() {
        log.info("Fetching all WritoNews");
        List<WritoNews> writoNewsList = writoNewsRepository.findAll();
        return writoNewsList.stream()
                .map(writoNewsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WritoNewsDTO> findById(Long id) {
        log.info("Fetching WritoNews by id: {}", id);
        return writoNewsRepository.findById(id)
                .map(writoNewsMapper::entityToDto);
    }

    @Override
    public WritoNewsDTO insert(WritoNewsDTO writoNewsDTO) {
        log.info("Inserting WritoNews: {}", writoNewsDTO);
        WritoNews writoNewsEntity = writoNewsMapper.dtoToEntity(writoNewsDTO);
        writoNewsEntity = writoNewsRepository.save(writoNewsEntity);
        return writoNewsMapper.entityToDto(writoNewsEntity);
    }

    @Override
    public WritoNewsDTO update(WritoNewsDTO writoNewsDTO) {
        log.info("Updating WritoNews: {}", writoNewsDTO);
        WritoNews existingWritoNews = writoNewsRepository.findById(writoNewsDTO.getWritonewsId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.writoNewsTable));
        WritoNews writoNewsEntity = writoNewsMapper.existingEntityToNewEntity(writoNewsDTO, existingWritoNews);
        return writoNewsMapper.entityToDto(writoNewsRepository.save(writoNewsEntity));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting WritoNews with id: {}", id);
        writoNewsRepository.deleteById(id);
    }

    @Override
    public List<WritoNewsDTO> findByTitleContaining(String title) {
        log.info("Fetching WritoNews with title containing: {}", title);
        List<WritoNews> writoNewsList = writoNewsRepository.findByTitleContaining(title);
        return writoNewsList.stream()
                .map(writoNewsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WritoNewsDTO> findByDescriptionContaining(String keyword) {
        log.info("Fetching WritoNews with description containing: {}", keyword);
        List<WritoNews> writoNewsList = writoNewsRepository.findByDescriptionContaining(keyword);
        return writoNewsList.stream()
                .map(writoNewsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WritoNewsDTO> findLatestNews(int limit) {
        log.info("Fetching latest {} WritoNews", limit);
        List<WritoNews> writoNewsList = writoNewsRepository.findLatestNews(limit);
        return writoNewsList.stream()
                .map(writoNewsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<WritoNewsDTO> findAllPaginated(Pageable pageable) {
        log.info("Fetching paginated WritoNews");
        Page<WritoNews> writoNewsPage = writoNewsRepository.findAll(pageable);
        return writoNewsPage.map(writoNewsMapper::entityToDto);
    }
}