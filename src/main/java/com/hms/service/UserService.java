package com.hms.service;


import com.hms.entity.AppUser;
import com.hms.payload.AppUserDto;
import com.hms.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;

    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser createUser(AppUserDto appUserDto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUserDto.getUsername());
        if (opUsername.isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUserDto.getEmail());
        if (opEmail.isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        // Convert AppUserDto to AppUser
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDto.getUsername());
        appUser.setEmail(appUserDto.getEmail());
        String encryptedPassword = BCrypt.hashpw(appUserDto.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(encryptedPassword);

        return appUserRepository.save(appUser);
    }
}
