package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.enums.ConstUdacity;
import com.udacity.jwdnd.course1.cloudstorage.enums.UriParam;
import com.udacity.jwdnd.course1.cloudstorage.models.MessageForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping(UriParam.SIGNUP)
@RequiredArgsConstructor
public class SignUpController {
    private final UserService UserService;
    @GetMapping
    public String signupView(){
        return ConstUdacity.SIGNUP_VIEW;
    }

    @PostMapping
    public String signUp(@ModelAttribute(ConstUdacity.MODEl_ATTRIBUTE_SIGNUP) User user, Model model) {
        MessageForm messageForm = new MessageForm();
        if (!UserService.isExistAccount(user.getUsername())) {
            messageForm.setMessage(ConstUdacity.MSG_EXIST_ACCOUNT);
        }
        if (Objects.isNull(messageForm.getMessage())) {
            int isSuccess = UserService.signUp(user);
            if (isSuccess < ConstUdacity.ZERO) {
                messageForm.setMessage(ConstUdacity.MSG_CREATE_ACCOUNT_FAILURE);
            }
        }
        if (Objects.isNull(messageForm.getMessage())) {
            model.addAttribute(ConstUdacity.MODEL_ATTRIBUTE_SIGNUP_SUCCESS, true);
        } else {
            model.addAttribute(ConstUdacity.MODEL_ATTRIBUTE_SIGNUP_FAILURE, messageForm.getMessage());
        }

        return ConstUdacity.SIGNUP_VIEW;
    }
}
