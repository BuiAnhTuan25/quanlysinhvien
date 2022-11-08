package com.huce.quanlysinhvien.service.impl;

import com.huce.quanlysinhvien.model.entity.UsersEntity;
import com.huce.quanlysinhvien.repository.UserRepository;
import com.huce.quanlysinhvien.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

        return new CustomUserDetails(user);
    }
}
