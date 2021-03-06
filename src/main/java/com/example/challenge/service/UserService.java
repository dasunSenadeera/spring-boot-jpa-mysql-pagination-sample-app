package com.example.challenge.service;

import com.example.challenge.interfaces.UserDetails;
import com.example.challenge.models.User;
import com.example.challenge.repository.OrganizationRepository;
import com.example.challenge.repository.UserRepository;
import com.example.challenge.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    public List<User> saveInitialUsers(List<UserRequest> userRequestListList){
        userRequestListList.forEach(userRequest -> {
            userRepository.save(new User(userRequest.get_id(), userRequest.getUrl(), userRequest.getExternalId(), userRequest.getName(), userRequest.getAlias(), userRequest.getCreated_at(), userRequest.isActive(), userRequest.isVerified(), userRequest.isShared(), userRequest.getLocale(), userRequest.getTimezone(), userRequest.getLast_login_at(), userRequest.getEmail(), userRequest.getPhone(), userRequest.getSignature(), organizationRepository.findById((Long.valueOf(userRequest.getOrganization_id()))).orElse(null), userRequest.getTags(), userRequest.isSuspended(), userRequest.getRole()));
        });
        return userRepository.findAll();
    }

    public Optional<User> getUser(long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByName(String name){
        return userRepository.findByName(name);
    }

    public Optional<User> findUserByUrl(String url){
        return userRepository.findByUrl(url);
    }

    public Optional<User> findUserByExternalId(String externalId){
        return userRepository.findByExternalId(externalId);
    }

    public Optional<User> findUserByEmail(String externalId){
        return userRepository.findByEmail(externalId);
    }

    public Optional<User> findUserByPhone(String externalId){
        return userRepository.findByPhone(externalId);
    }

    public Page<User> findByActive(boolean active, Pageable pageable){
        return userRepository.findByActive(active, pageable);
    }

    public Page<User> findByVerified(boolean verified, Pageable pageable){
        return userRepository.findByVerified(verified, pageable);
    }

    public Page<User> findByShared(boolean shared, Pageable pageable){
        return userRepository.findByShared(shared, pageable);
    }

    public Page<User> findBySuspended(boolean suspended, Pageable pageable){
        return userRepository.findBySuspended(suspended, pageable);
    }

    public Page<User> findByRole(String role, Pageable pageable){
        return userRepository.findByRole(role, pageable);
    }

    public Page<User> findTicketsByTagNames(List<String> tagslist, Pageable pageable){
        return userRepository.findByTagsIn(tagslist, pageable);
    }

    public Page<User> findAllWithPagination(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Page<User> findByLocale(Locale locale, Pageable pageable){
        return userRepository.findByLocale(locale, pageable);
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }

//    public List<UserDetails> getUserBasicDetails(){
//        return userRepository.getUserBasicDetails();
//    }
//
//    public Page<User> findByTimeZone(TimeZone timeZone, int page, int size){
//        return userRepository.findByTimeZone(timeZone, PageRequest.of(page, size));
//    }

}
