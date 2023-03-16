package com.twlone.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twlone.entity.Authorization;
import com.twlone.repository.AuthorizationRepository;

@Service
public class UserDetailService implements UserDetailsService {
    private final AuthorizationRepository authorizationRepository;

    public UserDetailService(AuthorizationRepository repository) {
        this.authorizationRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Authorization> authorization = authorizationRepository.findByUserUserIdEquals(username);
        if(!authorization.isPresent()) {
            throw new UsernameNotFoundException("Exception: UserName Not Found");
        }
        return new UserDetail(authorization.get());
    }
}
