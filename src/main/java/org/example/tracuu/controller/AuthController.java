package org.example.tracuu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "T\u00EAn \u0111\u0103ng nh\u1EADp ho\u1EB7c m\u1EADt kh\u1EA9u kh\u00F4ng \u0111\u00FAng!");
        }
        if (logout != null) {
            model.addAttribute("success", "\u0110\u0103ng xu\u1EA5t th\u00E0nh c\u00F4ng!");
        }
        return "login";
    }
    
    // @GetMapping("/simple-login")
    // public String simpleLoginPage() {
    //     return "simple-login";
    // }
}
