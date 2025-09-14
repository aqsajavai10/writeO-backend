package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.Novels;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface NovelsRepository extends JpaRepository<Novels, Long> {
    List<Novels> findAllByAuthorId(Long authorId);
    List<Novels> findAllByGenre(String genre);
    List<Novels> findAllByCompletionStatus(Character status);
    List<Novels> findByNovelNameContainingIgnoreCase(String novelName);

    @Query("SELECT n FROM Novels n ORDER BY n.novelName ASC")
    List<Novels> findAllNovelsOrderByNameAsc();

    @Query("SELECT n FROM Novels n ORDER BY n.novelName DESC")
    List<Novels> findAllNovelsOrderByNameDesc();

    @Query("SELECT COUNT(v) FROM Volumes v WHERE v.novelId = :novelId")
    int countTotalVolumesByNovelId(@Param("novelId") Long novelId);

    void deleteByAuthorId(Long authorId);

    // Add this method
    boolean existsByNovelName(String novelName);
}