package com.example.challenge.repository;


import com.example.challenge.interfaces.UserDetails;
import com.example.challenge.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUrl(String url);

    Optional<User> findByExternalId(String externalId);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String name);

    Optional<User> findByPhone(String name);

    Page<User> findByActive(boolean active, Pageable pageable);

    Page<User> findByVerified(boolean verified, Pageable pageable);

    Page<User> findByShared(boolean shared, Pageable pageable);

    Page<User> findBySuspended(boolean suspended, Pageable pageable);

    Page<User> findByRole(String role, Pageable pageable);

    Page<User> findByLocale(Locale locale, Pageable pageable);
//
    Page<User> findByTagsIn(Collection<String> tags, Pageable pageable);

//    @Query("SELECT u from User u")
//    List<UserDetails> getUserBasicDetails();
}
