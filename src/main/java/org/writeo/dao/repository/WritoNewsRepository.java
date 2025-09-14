package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.WritoNews;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface WritoNewsRepository extends JpaRepository<WritoNews, Long> {

    @Query("SELECT wn FROM WritoNews wn WHERE LOWER(wn.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<WritoNews> findByTitleContaining(@Param("title") String title);

    @Query("SELECT wn FROM WritoNews wn WHERE LOWER(wn.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<WritoNews> findByDescriptionContaining(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM " + CommonConstants.writoNewsTable + " ORDER BY writonews_id DESC LIMIT :limit", nativeQuery = true)
    List<WritoNews> findLatestNews(@Param("limit") int limit);
}