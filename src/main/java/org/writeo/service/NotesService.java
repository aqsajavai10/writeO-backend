package org.writeo.service;

import org.writeo.dao.dto.NotesDTO;

import java.util.List;

public interface NotesService {
    List<NotesDTO> findAll();
    NotesDTO findById(Long id);
    List<NotesDTO> findAllByChapterId(Long chapterId);
    List<NotesDTO> findAllByAuthorId(String authorId);
    List<NotesDTO> findAllByType(String type);
    NotesDTO insert(NotesDTO notesDTO);
    NotesDTO update(NotesDTO notesDTO);
    void delete(Long id);
    void deleteByChapterId(Long chapterId);
    Long getMaxNoteId();
}