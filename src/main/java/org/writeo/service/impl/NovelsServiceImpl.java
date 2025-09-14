package org.writeo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.writeo.dao.mapper.NovelsMapper;
import org.writeo.dao.model.Novels;
import org.writeo.dao.repository.NovelsRepository;
import org.writeo.dao.dto.NovelsDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.NovelsService;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.NovelAlreadyExistsException;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class NovelsServiceImpl implements NovelsService {

    private final NovelsRepository novelsRepository;
    private final NovelsMapper novelsMapper;

    @Override
    public List<NovelsDTO> findAll() {
        log.info("Fetching all Novels");
        return novelsRepository.findAll().stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovelsDTO findById(Long id) throws CustomNoSuchRecordExistsException {
        log.info("Fetching Novel by id: {}", id);
        return novelsRepository.findById(id)
                .map(novelsMapper::entityToDto)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.novelsTable));
    }

    @Override
    public List<NovelsDTO> findAllByAuthorId(Long authorId) {
        log.info("Fetching Novels by author id: {}", authorId);
        return novelsRepository.findAllByAuthorId(authorId).stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NovelsDTO> findAllByGenre(String genre) {
        log.info("Fetching Novels by genre: {}", genre);
        return novelsRepository.findAllByGenre(genre).stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NovelsDTO> findAllByCompletionStatus(Character status) {
        log.info("Fetching Novels by completion status: {}", status);
        return novelsRepository.findAllByCompletionStatus(status).stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovelsDTO insert(NovelsDTO novelsDTO) {
        log.info("Inserting Novel: {}", novelsDTO);
        novelsDTO.setStartDate(new Date());
        Novels novelsEntity = novelsMapper.dtoToEntity(novelsDTO);
        return novelsMapper.entityToDto(novelsRepository.save(novelsEntity));
    }

    @Override
    public NovelsDTO update(NovelsDTO novelsDTO) throws CustomNoSuchRecordExistsException {
        log.info("Updating Novel: {}", novelsDTO);
        Novels existingNovel = novelsRepository.findById(novelsDTO.getNovelId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.novelsTable));
        Novels novelsEntity = novelsMapper.existingEntityToNewEntity(novelsDTO, existingNovel);
        return novelsMapper.entityToDto(novelsRepository.save(novelsEntity));
    }

    @Override
    public void delete(Long id) throws CustomNoSuchRecordExistsException {
        log.info("Deleting Novel with id: {}", id);
        novelsRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.novelsTable));
        novelsRepository.deleteById(id);
    }

    @Override
    public void deleteByAuthorId(Long authorId) {
        log.info("Deleting Novels by author id: {}", authorId);
        novelsRepository.deleteByAuthorId(authorId);
    }

    @Override
    public List<NovelsDTO> findNovelsByName(String name) {
        log.info("Finding Novels by name: {}", name);
        return novelsRepository.findByNovelNameContainingIgnoreCase(name).stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovelsDTO createNovel(NovelsDTO novelsDTO) throws IOException {
        log.info("Creating Novel: {}", novelsDTO);
        if (novelsRepository.existsByNovelName(novelsDTO.getNovelName())) {
            throw new NovelAlreadyExistsException("Novel with name " + novelsDTO.getNovelName() + " already exists.");
        }

        Novels novel = novelsMapper.dtoToEntity(novelsDTO);

        novel = novelsRepository.save(novel);
        return novelsMapper.entityToDto(novel);
    }

    @Override
    public List<NovelsDTO> getAllNovelsSortedByNameAsc() {
        log.info("Fetching all Novels sorted by name ascending");
        return novelsRepository.findAllNovelsOrderByNameAsc().stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NovelsDTO> getAllNovelsSortedByNameDesc() {
        log.info("Fetching all Novels sorted by name descending");
        return novelsRepository.findAllNovelsOrderByNameDesc().stream()
                .map(novelsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalVolumesByNovelId(Long novelId) {
        log.info("Getting total volumes for Novel with id: {}", novelId);
        return novelsRepository.countTotalVolumesByNovelId(novelId);
    }

    @Override
    public boolean markNovelAsCompleted(Long novelId) {
        log.info("Marking Novel as completed, id: {}", novelId);
        Optional<Novels> optionalNovel = novelsRepository.findById(novelId);
        if (optionalNovel.isPresent()) {
            Novels novel = optionalNovel.get();
            novel.setCompletionStatus('Y');
            novel.setEndDate(new Date());
            novelsRepository.save(novel);
            return true;
        }
        return false;
    }
}