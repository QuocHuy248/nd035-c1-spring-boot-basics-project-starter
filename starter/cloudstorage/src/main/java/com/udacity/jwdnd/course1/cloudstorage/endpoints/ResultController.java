package com.udacity.jwdnd.course1.cloudstorage.endpoints;

import com.udacity.jwdnd.course1.cloudstorage.enums.ConstUdacity;
import com.udacity.jwdnd.course1.cloudstorage.enums.UriParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {
    @GetMapping(UriParam.RESULT)
    public String getResultPage(Model model){
        model.addAttribute("success",true);
        return ConstUdacity.RESULT_VIEW;
    }
}