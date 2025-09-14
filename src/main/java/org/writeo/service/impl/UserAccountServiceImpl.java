package org.writeo.service.impl;

import org.writeo.dao.mapper.UserAccountMapper;
import org.writeo.dao.model.UserAccount;
import org.writeo.dao.repository.UserAccountRepository;
import org.writeo.dao.dto.UserAccountDTO;
import org.writeo.exceps.CustomNoSuchRecordExistsException;
import org.writeo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private final UserAccountRepository userAccountRepository;
    @Autowired
    private final UserAccountMapper userAccountMapper;

    @Override
    public List<UserAccountDTO> findAll() {
        log.info(log.isInfoEnabled() ? "Fetching all UserAccounts" : " ");
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        return userAccountList.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public UserAccountDTO findById(String userId) {
        log.info(log.isInfoEnabled() ? "Fetching UserAccount by id: " + userId : " ");
        UserAccount userAccountEntity = userAccountRepository.findById(userId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userAccountTable));
        return userAccountMapper.entityToDto(userAccountEntity);
    }

    @Override
    public List<UserAccountDTO> findAllByFname(String fname) {
        log.info(log.isInfoEnabled() ? "Fetching UserAccounts by first name: " + fname : " ");
        List<UserAccount> userAccountList = userAccountRepository.findAllByFname(fname);
        return userAccountList.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAccountDTO> findAllByLname(String lname) {
        log.info(log.isInfoEnabled() ? "Fetching UserAccounts by last name: " + lname : " ");
        List<UserAccount> userAccountList = userAccountRepository.findAllByLname(lname);
        return userAccountList.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserAccountDTO> findByAlias(String alias) {
        log.info(log.isInfoEnabled() ? "Fetching UserAccount by alias: " + alias : " ");
        return userAccountRepository.findByAlias(alias)
                .map(userAccountMapper::entityToDto);
    }

    @Override
    @SneakyThrows
    public UserAccountDTO insert(UserAccountDTO userAccountDTO) {
        log.info(log.isInfoEnabled() ? "Inserting UserAccount: " + userAccountDTO : " ");
        UserAccount userAccountEntity = userAccountMapper.dtoToEntity(userAccountDTO);
        userAccountRepository.save(userAccountEntity);
        return userAccountMapper.entityToDto(userAccountEntity);
    }

    @Override
    @SneakyThrows
    public UserAccountDTO update(UserAccountDTO userAccountDTO) {
        log.info(log.isInfoEnabled() ? "Updating UserAccount: " + userAccountDTO : " ");
        UserAccount existingUserAccount = userAccountRepository.findById(userAccountDTO.getUserId())
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userAccountTable));
        UserAccount userAccountEntity = userAccountMapper.existingEntityToNewEntity(userAccountDTO, existingUserAccount);
        return userAccountMapper.entityToDto(userAccountRepository.save(userAccountEntity));
    }

    @Override
    @SneakyThrows
    public void delete(String userId) {
        log.info(log.isInfoEnabled() ? "Deleting UserAccount with id: " + userId : " ");
        UserAccount userAccountEntity = userAccountRepository.findById(userId)
                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userAccountTable));
        userAccountRepository.delete(userAccountEntity);
    }

    @Override
    public List<UserAccountDTO> findByNameContaining(String name) {
        log.info(log.isInfoEnabled() ? "Searching UserAccounts by name containing: " + name : " ");
        List<UserAccount> userAccountList = userAccountRepository.findByNameContaining(name);
        return userAccountList.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAccountDTO> getAllUserAccountsSortedByNameAsc() {
        log.info(log.isInfoEnabled() ? "Fetching all UserAccounts sorted by name ascending" : " ");
        List<UserAccount> userAccountList = userAccountRepository.findAllUserAccountsOrderByNameAsc();
        return userAccountList.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalUserAccountsCount() {
        log.info(log.isInfoEnabled() ? "Getting total count of UserAccounts" : " ");
        return userAccountRepository.countTotalUserAccounts();
    }

    @Override
    public boolean existsByAlias(String alias) {
        log.info(log.isInfoEnabled() ? "Checking if UserAccount exists by alias: " + alias : " ");
        return userAccountRepository.existsByAlias(alias);
    }

    @Override
    public List<UserAccountDTO> insertBatch(List<UserAccountDTO> userAccountDTOList) {
        log.info(log.isInfoEnabled() ? "Inserting batch of UserAccounts" : " ");
        List<UserAccount> userAccountEntities = userAccountDTOList.stream()
                .map(userAccountMapper::dtoToEntity)
                .collect(Collectors.toList());
        List<UserAccount> savedEntities = userAccountRepository.saveAll(userAccountEntities);
        return savedEntities.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public List<UserAccountDTO> updateBatch(List<UserAccountDTO> userAccountDTOList) {
        log.info(log.isInfoEnabled() ? "Updating batch of UserAccounts" : " ");
        List<UserAccount> updatedEntities = userAccountDTOList.stream()
                .map(dto -> {
                    UserAccount existingUserAccount = null;
                    try {
                        existingUserAccount = userAccountRepository.findById(dto.getUserId())
                                .orElseThrow(() -> new CustomNoSuchRecordExistsException(CommonConstants.userAccountTable));
                    } catch (CustomNoSuchRecordExistsException e) {
                        throw new RuntimeException(e);
                    }
                    return userAccountMapper.existingEntityToNewEntity(dto, existingUserAccount);
                })
                .collect(Collectors.toList());
        List<UserAccount> savedEntities = userAccountRepository.saveAll(updatedEntities);
        return savedEntities.stream()
                .map(userAccountMapper::entityToDto)
                .collect(Collectors.toList());
    }
}