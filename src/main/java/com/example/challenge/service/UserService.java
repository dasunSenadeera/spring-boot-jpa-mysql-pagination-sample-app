package com.example.challenge.service;

import com.example.challenge.interfaces.UserDetails;
import com.example.challenge.models.User;
import com.example.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> saveInitialUsers(List<User> userList){
        userList.forEach(user -> {
            userRepository.save(user);
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

    public Page<User> findByActive(boolean active, int page, int size){
        return userRepository.findByActive(active, PageRequest.of(page, size));
    }

    public Page<User> findByVerified(boolean verified, int page, int size){
        return userRepository.findByVerified(verified, PageRequest.of(page, size));
    }

    public Page<User> findByShared(boolean shared, int page, int size){
        return userRepository.findByShared(shared, PageRequest.of(page, size));
    }

    public Page<User> findBySuspended(boolean suspended, int page, int size){
        return userRepository.findBySuspended(suspended, PageRequest.of(page, size));
    }

    public Page<User> findByRole(String role, int page, int size){
        return userRepository.findByRole(role, PageRequest.of(page, size));
    }

    public Page<User> findTicketsByTagNames(List<String> tagslist, int page, int size){
        return userRepository.findByTagsIn(tagslist, PageRequest.of(page, size));
    }

    public Page<User> findAllWithPagination(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<User> findByLocale(Locale locale, int page, int size){
        return userRepository.findByLocale(locale, PageRequest.of(page, size));
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
