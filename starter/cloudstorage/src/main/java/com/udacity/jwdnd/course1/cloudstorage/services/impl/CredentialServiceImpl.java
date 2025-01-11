package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final CredentialMapper credentialMapper;

    @Override
    public Integer createCredential(Credential credential) {
        return credentialMapper.create(credential);
    }

    @Override
    public void updateCredential(Credential credential) {
        credentialMapper.update(credential);
    }

    @Override
    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    @Override
    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    @Override
    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }
}