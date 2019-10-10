package com.example.challenge.service;

import com.example.challenge.models.Organization;
import com.example.challenge.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> saveInitialOrganizations(List<Organization> organizationList){
        organizationList.forEach(user -> {
            organizationRepository.save(user);
        });
        return organizationRepository.findAll();
    }

    public Organization getOrganization(long id){
        return organizationRepository.findById(id).orElse(null);
    }

    public Organization getOrganizationsByName(String name){
        return organizationRepository.findByName(name);
    }

    public List<Organization> findOrganizationByDomainName(List<String> domainnamelist){
        return organizationRepository.findByDomainIn(domainnamelist);
    }

    public List<Organization> findOrganizationByTagNames(List<String> tagslist, int page, int size){
        return organizationRepository.findByTagsIn(tagslist, PageRequest.of(page, size));
    }

    public Organization findOrganizationByUrl(String url){
        return organizationRepository.findByUrl(url);
    }

    public Organization findOrganizationByExternalId(String externalId){
        return organizationRepository.findByExternalId(externalId);
    }

    public Page<Organization> findOrganizationBySharedTickets(boolean sharedTickets, int page, int size){
        return organizationRepository.findBySharedTickets(sharedTickets, PageRequest.of(page, size));
    }

    public List<Organization> findOrganizationsByDetail(String detail){
        return organizationRepository.findByDetails(detail);
    }

    public List<Organization> findAllByCreatedAt(LocalDateTime dateTime){
        return organizationRepository.findAllByCreatedAt(dateTime);
    }

    public List<Organization> findAllByBeforeCreatedAt(LocalDateTime dateTime){
        return organizationRepository.findAllWithCreationDateTimeBefore(dateTime);
    }

    public List<Organization> findAllByAfterCreatedAt(LocalDateTime dateTime){
        return organizationRepository.findAllWithCreationDateTimeAfter(dateTime);
    }

    public List<Organization> findAllOrganizationsCreatedBetween(LocalDateTime start, LocalDateTime end){
        return organizationRepository.findAllByCreatedAtBetween(start, end);
    }

    public Page<Organization> findAllOrganizationsWithPagination(int page, int size){
        return organizationRepository.findAll(PageRequest.of(page, size));
    }

    public List<Organization> findAll(){
        return organizationRepository.findAll();
    }
}
