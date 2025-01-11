package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getUser(String username);
    Integer signUp(User user);
    boolean isExistAccount(String userName);
}