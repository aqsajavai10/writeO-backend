package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.UserAccount;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    @Query(value = "SELECT * FROM " + CommonConstants.userAccountTable + " WHERE fname = :fname", nativeQuery = true)
    List<UserAccount> findAllByFname(String fname);

    @Query(value = "SELECT * FROM " + CommonConstants.userAccountTable + " WHERE lname = :lname", nativeQuery = true)
    List<UserAccount> findAllByLname(String lname);

    @Query(value = "SELECT * FROM " + CommonConstants.userAccountTable + " WHERE alias = :alias", nativeQuery = true)
    Optional<UserAccount> findByAlias(String alias);

    @Query("SELECT u FROM UserAccount u WHERE LOWER(u.fname) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.lname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<UserAccount> findByNameContaining(@Param("name") String name);

    @Query("SELECT u FROM UserAccount u ORDER BY u.fname ASC, u.lname ASC")
    List<UserAccount> findAllUserAccountsOrderByNameAsc();

    @Query("SELECT COUNT(u) FROM UserAccount u")
    Long countTotalUserAccounts();

    boolean existsByAlias(String alias);
}