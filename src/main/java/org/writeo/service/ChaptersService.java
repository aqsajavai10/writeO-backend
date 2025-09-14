package org.writeo.service;

import org.writeo.dao.dto.ChaptersDTO;

import java.util.List;

public interface ChaptersService {
    List<ChaptersDTO> findAll();
    ChaptersDTO findById(Long id);
    List<ChaptersDTO> findAllByNovelId(Long novelId);
    ChaptersDTO findLatestChapterByNovelId(Long novelId);
    ChaptersDTO insert(ChaptersDTO chaptersDTO);
    ChaptersDTO update(ChaptersDTO chaptersDTO);
    void delete(Long id);
    void deleteByNovelId(Long novelId);
    Integer getTotalWordCountByNovelId(Long novelId);
    Long getMaxChapterId();
}