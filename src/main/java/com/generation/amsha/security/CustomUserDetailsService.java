package com.generation.amsha.security;

import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.model.Role;
import com.generation.amsha.user.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAccountDao userAccountDao;
    @Autowired
    public CustomUserDetailsService(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountDao.getUserByEmail(username);
        return new User(user.getEmail(), user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthorities(Role role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }
}
