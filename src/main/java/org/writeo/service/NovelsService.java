package org.writeo.service;

import org.springframework.web.multipart.MultipartFile;
import org.writeo.dao.dto.NovelsDTO;
import org.writeo.dao.model.Novels;
import org.writeo.exceps.CustomNoSuchRecordExistsException;

import java.io.IOException;
import java.util.List;

public interface NovelsService {
    List<NovelsDTO> findAll();
    NovelsDTO findById(Long id) throws CustomNoSuchRecordExistsException;
    List<NovelsDTO> findAllByAuthorId(Long authorId);
    List<NovelsDTO> findAllByGenre(String genre);
    List<NovelsDTO> findAllByCompletionStatus(Character status);
    NovelsDTO insert(NovelsDTO novelsDTO);
    NovelsDTO update(NovelsDTO novelsDTO) throws CustomNoSuchRecordExistsException;
    void delete(Long id) throws CustomNoSuchRecordExistsException;
    void deleteByAuthorId(Long authorId);
    List<NovelsDTO> findNovelsByName(String name);
    NovelsDTO createNovel(NovelsDTO novelsDTO) throws IOException;
    List<NovelsDTO> getAllNovelsSortedByNameAsc();
    List<NovelsDTO> getAllNovelsSortedByNameDesc();
    int getTotalVolumesByNovelId(Long novelId);
    boolean markNovelAsCompleted(Long novelId);
}