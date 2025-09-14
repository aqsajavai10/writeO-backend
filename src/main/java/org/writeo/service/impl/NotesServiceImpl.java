package org.writeo.service.impl;

import org.writeo.dao.dto.NotesDTO;
import org.writeo.dao.mapper.NotesMapper;
import org.writeo.dao.model.Notes;
import org.writeo.dao.repository.NotesRepository;
import org.writeo.service.NotesService;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.writeo.utils.exceps.CustomNoSuchRecordExistsException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

    @Autowired
    private final NotesRepository notesRepository;
    @Autowired
    private final NotesMapper notesMapper;

    @Override
    public List<NotesDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all Notes" : " ");
        List<Notes> notesList = notesRepository.findAll();
        return notesList.stream()
                .map(notesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public NotesDTO findById(Long id) {
        log.info(log.isInfoEnabled() ? "Fetching Note by id: " + id : " ");
        Notes notesEntity = notesRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.notesTable));
        return notesMapper.entityToDto(notesEntity);
    }

    @Override
    public List<NotesDTO> findAllByChapterId(Long chapterId) {
        log.info(log.isInfoEnabled() ? "Fetching Notes by chapter id: " + chapterId : " ");
        List<Notes> notesList = notesRepository.findAllByChapterId(chapterId);
        return notesList.stream()
                .map(notesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotesDTO> findAllByAuthorId(String authorId) {
        log.info(log.isInfoEnabled() ? "Fetching Notes by author id: " + authorId : " ");
        List<Notes> notesList = notesRepository.findAllByAuthorId(authorId);
        return notesList.stream()
                .map(notesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotesDTO> findAllByType(String type) {
        log.info(log.isInfoEnabled() ? "Fetching Notes by type: " + type : " ");
        List<Notes> notesList = notesRepository.findAllByType(type);
        return notesList.stream()
                .map(notesMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public NotesDTO insert(NotesDTO notesDTO) {
        log.info(log.isInfoEnabled() ? "Inserting Note: " + notesDTO : " ");
        notesDTO.setCreationDate(new Date().toString());
        notesDTO.setLastUpdatedAt(new Date());
        Notes notesEntity = notesMapper.dtoToEntity(notesDTO);
        notesRepository.save(notesEntity);
        return notesMapper.entityToDto(notesEntity);
    }

    @Override
    @SneakyThrows
    public NotesDTO update(NotesDTO notesDTO) {
        log.info(log.isInfoEnabled() ? "Updating Note: " + notesDTO : " ");
        Notes existingNote = notesRepository.findById(notesDTO.getNoteId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.notesTable));
        notesDTO.setLastUpdatedAt(new Date());
        Notes notesEntity = notesMapper.existingEntityToNewEntity(notesDTO, existingNote);
        return notesMapper.entityToDto(notesRepository.save(notesEntity));
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        log.info(log.isInfoEnabled() ? "Deleting Note with id: " + id : " ");
        Notes notesEntity = notesRepository.findById(id)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.notesTable));
        notesRepository.delete(notesEntity);
    }

    @Override
    public void deleteByChapterId(Long chapterId) {
        log.info(log.isInfoEnabled() ? "Deleting Notes by chapter id: " + chapterId : " ");
        List<Notes> notesList = notesRepository.findAllByChapterId(chapterId);
        notesRepository.deleteAll(notesList);
    }

    @Override
    public Long getMaxNoteId() {
        return notesRepository.findMaxNoteId();
    }
}