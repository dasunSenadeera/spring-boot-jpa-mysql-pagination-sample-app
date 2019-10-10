package com.example.challenge.repository;

import com.example.challenge.models.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByName(String name);

    Organization findByUrl(String url);

    Organization findByExternalId(String externalId);

    List<Organization> findByDomainIn(Collection<String> names);

    List<Organization> findByTagsIn(Collection<String> tags, Pageable pageable);

    List<Organization> findByDetails(String details);

    Page<Organization> findBySharedTickets(boolean sharedTicket, Pageable pageable);

    List<Organization> findAllByCreatedAt(LocalDateTime createdDate);

    List<Organization> findAllByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    @Query("select o from Organization o where o.createdAt <= :createdDate")
    List<Organization> findAllWithCreationDateTimeBefore(LocalDateTime createdDate);

    @Query("select o from Organization o where o.createdAt >= :createdDate")
    List<Organization> findAllWithCreationDateTimeAfter(LocalDateTime createdDate);
}
