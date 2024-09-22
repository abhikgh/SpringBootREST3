package com.example.SpringBootREST3.service;

import com.example.SpringBootREST3.entity.UserMaster;
import com.example.SpringBootREST3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserMaster userMaster = userRepository.findByUserId(userId);

        if (userMaster == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return User
                .builder()
                .username(userMaster.getUserId())
                .password(userMaster.getPassword())
                .roles(userMaster.getRoleCode())
                .build();

    }


}
