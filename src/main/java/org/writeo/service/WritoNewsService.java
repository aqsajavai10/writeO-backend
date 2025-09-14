package org.writeo.service;

import org.writeo.dao.dto.WritoNewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WritoNewsService {
    List<WritoNewsDTO> findAll();
    Optional<WritoNewsDTO> findById(Long id);
    WritoNewsDTO insert(WritoNewsDTO writoNewsDTO);
    WritoNewsDTO update(WritoNewsDTO writoNewsDTO);
    void delete(Long id);
    List<WritoNewsDTO> findByTitleContaining(String title);
    List<WritoNewsDTO> findByDescriptionContaining(String keyword);
    List<WritoNewsDTO> findLatestNews(int limit);
    Page<WritoNewsDTO> findAllPaginated(Pageable pageable);
}