package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.Volumes;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;

@Repository
public interface VolumesRepository extends JpaRepository<Volumes, Long> {

    @Query(value = "SELECT * FROM " + CommonConstants.volumesTable + " WHERE novel_id = :novelId", nativeQuery = true)
    List<Volumes> findAllByNovelId(Long novelId);

    @Query(value = "SELECT * FROM " + CommonConstants.volumesTable + " WHERE novel_id = :novelId AND volume_number = :volumeNumber", nativeQuery = true)
    Volumes findByNovelIdAndVolumeNumber(Long novelId, Integer volumeNumber);

    @Query(value = "SELECT MAX(volume_number) FROM " + CommonConstants.volumesTable + " WHERE novel_id = :novelId", nativeQuery = true)
    Integer findMaxVolumeNumberByNovelId(Long novelId);

    @Query(value = "SELECT MAX(volume_id) FROM " + CommonConstants.volumesTable, nativeQuery = true)
    Long findMaxVolumeId();
}