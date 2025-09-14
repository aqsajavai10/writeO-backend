package org.writeo.service;

import org.writeo.dao.dto.UserAccountDTO;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    List<UserAccountDTO> findAll();
    UserAccountDTO findById(String userId);
    List<UserAccountDTO> findAllByFname(String fname);
    List<UserAccountDTO> findAllByLname(String lname);
    Optional<UserAccountDTO> findByAlias(String alias);
    UserAccountDTO insert(UserAccountDTO userAccountDTO);
    UserAccountDTO update(UserAccountDTO userAccountDTO);
    void delete(String userId);
    List<UserAccountDTO> findByNameContaining(String name);
    List<UserAccountDTO> getAllUserAccountsSortedByNameAsc();
    Long getTotalUserAccountsCount();
    boolean existsByAlias(String alias);
    List<UserAccountDTO> insertBatch(List<UserAccountDTO> userAccountDTOList);
    List<UserAccountDTO> updateBatch(List<UserAccountDTO> userAccountDTOList);
}