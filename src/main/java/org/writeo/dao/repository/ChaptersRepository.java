package org.writeo.dao.repository;

import org.writeo.dao.model.Chapters;
import org.writeo.utils.CommonConstants.CommonConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChaptersRepository extends JpaRepository<Chapters, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.chaptersTable + " WHERE novel_id = :novelId", nativeQuery = true)
    List<Chapters> findAllByNovelId(Long novelId);

    @Query(value = "SELECT * FROM " + CommonConstants.chaptersTable + " WHERE novel_id = :novelId ORDER BY ch_id DESC LIMIT 1", nativeQuery = true)
    Chapters findLatestChapterByNovelId(Long novelId);

    @Query(value = "SELECT SUM(word_count) FROM " + CommonConstants.chaptersTable + " WHERE novel_id = :novelId", nativeQuery = true)
    Integer getTotalWordCountByNovelId(Long novelId);

    @Query(value = "SELECT MAX(ch_id) FROM " + CommonConstants.chaptersTable, nativeQuery = true)
    Long findMaxChapterId();
}