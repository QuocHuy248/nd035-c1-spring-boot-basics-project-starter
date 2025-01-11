package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

import java.util.List;

public interface CredentialService {
    Integer createCredential(Credential credential);
    void updateCredential(Credential credential);
    void deleteCredential(Integer credentialId);
    List<Credential> getCredentials(Integer userId);
    Credential getCredential(Integer credentialId);
}