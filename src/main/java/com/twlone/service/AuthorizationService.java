package com.twlone.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.Authorization;
import com.twlone.repository.AuthorizationRepository;

@Service
public class AuthorizationService {
    private final AuthorizationRepository authorizationRepository;

    public AuthorizationService(AuthorizationRepository repository) {
        this.authorizationRepository = repository;
    }

    @Transactional
    public Authorization saveAuthorization(Authorization authorization) {
        return authorizationRepository.save(authorization);
    }
}
