package com.hms.service;






import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;

import java.util.Optional;

@Service
public class LoginService {
    private AppUserRepository appUserRepository;

    public LoginService(AppUserRepository appUserRepository, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }
    private JwtService jwtService;

    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();
         if( BCrypt.checkpw(dto.getPassword(),appUser.getPassword())){
             //generate Token
             String token = jwtService.generateToken(appUser.getUsername());
             return token;
         }
        }else {
            return null;
        }
        return null;
    }
}

