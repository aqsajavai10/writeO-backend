package org.writeo.web;

import org.writeo.dao.dto.VolumesDTO;
import org.writeo.service.VolumesService;
import org.writeo.utils.exceps.CustomNullPointerException;
import org.writeo.utils.exceps.CustomRecordAlreadyExistsException;
import org.writeo.utils.response.BackendResponse;
import org.writeo.utils.response.ResponseHandlerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/volumes")
@Log4j2
@AllArgsConstructor
public class VolumesController {

    @Autowired
    private final VolumesService volumesService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BackendResponse> getAllVolumes() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch all volumes." : null);
            List<VolumesDTO> volumes = volumesService.findAll();
            log.info(log.isInfoEnabled() ? "Volumes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(volumes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch volumes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> getVolumeById(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch volume by id: {}." : null, id);
            VolumesDTO volume = volumesService.findById(id);
            log.info(log.isInfoEnabled() ? "Volume fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(volume);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch volume: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByNovel/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getVolumesByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch volumes by novel id: {}." : null, novelId);
            List<VolumesDTO> volumes = volumesService.findAllByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Volumes fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(volumes);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch volumes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getByNovelAndNumber/{novelId}/{volumeNumber}", produces = "application/json")
    public ResponseEntity<BackendResponse> getVolumeByNovelIdAndVolumeNumber(@PathVariable Long novelId, @PathVariable Integer volumeNumber) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch volume by novel id: {} and volume number: {}." : null, novelId, volumeNumber);
            VolumesDTO volume = volumesService.findByNovelIdAndVolumeNumber(novelId, volumeNumber);
            log.info(log.isInfoEnabled() ? "Volume fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(volume);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch volume: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<BackendResponse> addVolume(@Valid @RequestBody VolumesDTO volume) {
        try {
            log.info(log.isInfoEnabled() ? "POST request received to add a volume." : null);
            VolumesDTO addedVolume = volumesService.insert(volume);
            log.info(log.isInfoEnabled() ? "Volume added successfully." : null);
            return ResponseHandlerUtil.handleSuccess(addedVolume);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to add volume: {}" : null, e.getMessage());
            throw e;
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<BackendResponse> updateVolume(@Valid @RequestBody VolumesDTO volume) {
        try {
            log.info(log.isInfoEnabled() ? "PUT request received to update a volume." : null);
            VolumesDTO updatedVolume = volumesService.update(volume);
            log.info(log.isInfoEnabled() ? "Volume updated successfully." : null);
            return ResponseHandlerUtil.handleSuccess(updatedVolume);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to update volume: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteVolume(@PathVariable Long id) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete a volume." : null);
            volumesService.delete(id);
            log.info(log.isInfoEnabled() ? "Volume deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Volume deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete volume: {}" : null, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteByNovel/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> deleteVolumesByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "DELETE request received to delete volumes by novel id: {}." : null, novelId);
            volumesService.deleteByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Volumes deleted successfully." : null);
            return ResponseHandlerUtil.handleSuccess("Volumes deleted successfully.");
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to delete volumes: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getMaxVolumeNumber/{novelId}", produces = "application/json")
    public ResponseEntity<BackendResponse> getMaxVolumeNumberByNovelId(@PathVariable Long novelId) {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch max volume number for novel id: {}." : null, novelId);
            Integer maxVolumeNumber = volumesService.getMaxVolumeNumberByNovelId(novelId);
            log.info(log.isInfoEnabled() ? "Max volume number fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(maxVolumeNumber);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch max volume number: {}" : null, e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/getMaxVolumeId", produces = "application/json")
    public ResponseEntity<BackendResponse> getMaxVolumeId() {
        try {
            log.info(log.isInfoEnabled() ? "GET request received to fetch max volume id." : null);
            Long maxVolumeId = volumesService.getMaxVolumeId();
            log.info(log.isInfoEnabled() ? "Max volume id fetched successfully." : null);
            return ResponseHandlerUtil.handleSuccess(maxVolumeId);
        } catch (CustomNullPointerException | CustomRecordAlreadyExistsException | DataAccessException e) {
            log.warn(log.isWarnEnabled() ? "Failed to fetch max volume id: {}" : null, e.getMessage());
            throw e;
        }
    }

}
