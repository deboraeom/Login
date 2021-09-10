package com.example.login10.Service;

import com.example.login10.Exception.UserNotFound;
import com.example.login10.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UserSecurityDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new  UsernameNotFoundException(" User not found"));
    }


}
