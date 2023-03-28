package com.example.auth.server.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.builder().username("1").password("$2y$10$rgsphd5UcSXbC./lOgYiu.5Qg25MRUMwP2IDn1D9mFIElunNyHcmK").roles("USER").build();
    }
}
