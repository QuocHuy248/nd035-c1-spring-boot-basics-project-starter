package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialController {
    private final EncryptionService encryptionService;
    private final CredentialService credentialService;
    private final UserService userService;

    @PostMapping("/save")
    public String createOrEdit(Credential credential, Model model, Authentication authentication) {
        User currentUser = userService.getUser(authentication.getName());
        Integer userId = currentUser.getUserid();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodeKey);
        credential.setKey(encodeKey);
        credential.setPassword(encryptedPassword);

        if(credential.getCredentialid() == null) {
            credential.setUserid(userId);
            credentialService.createCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }
        model.addAttribute("success", true);
        return "redirect:/result";
    }

    @RequestMapping("/delete/{credentialId}")
    public String delete(@PathVariable("credentialId") Integer credentialId) {
        credentialService.deleteCredential(credentialId);
        return "redirect:/result";
    }
}