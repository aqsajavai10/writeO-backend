package org.writeo.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.writeo.dao.model.UserAuth;
import org.writeo.utils.CommonConstants.CommonConstants;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {

    Optional<UserAuth> findByEmail(String email);

    @Query(value = "SELECT * FROM " + CommonConstants.userAuthTable + " WHERE last_login >= :date", nativeQuery = true)
    List<UserAuth> findAllLoggedInSince(@Param("date") Date date);

    @Query(value = "SELECT * FROM " + CommonConstants.userAuthTable + " WHERE last_login_device = :device", nativeQuery = true)
    List<UserAuth> findAllByLastLoginDevice(@Param("device") String device);

    @Query("SELECT ua FROM UserAuth ua WHERE ua.lastLogin IS NULL OR ua.lastLogin < :date")
    List<UserAuth> findInactiveUsers(@Param("date") Date date);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(ua) FROM UserAuth ua WHERE ua.lastLogin >= :startDate AND ua.lastLogin <= :endDate")
    long countActiveUsersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}