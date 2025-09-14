package org.writeo.dao.repository;

import org.writeo.dao.model.Notes;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.notesTable + " WHERE chapter_id = :chapterId", nativeQuery = true)
    List<Notes> findAllByChapterId(Long chapterId);

    @Query(value = "SELECT * FROM " + CommonConstants.notesTable + " WHERE author_id = :authorId", nativeQuery = true)
    List<Notes> findAllByAuthorId(String authorId);

    @Query(value = "SELECT * FROM " + CommonConstants.notesTable + " WHERE type = :type", nativeQuery = true)
    List<Notes> findAllByType(String type);

    @Query(value = "SELECT MAX(note_id) FROM " + CommonConstants.notesTable, nativeQuery = true)
    Long findMaxNoteId();
}