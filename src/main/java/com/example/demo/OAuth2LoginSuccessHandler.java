package com.example.demo;

import com.example.demo.Models.CustomOauth2User;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private  UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();

        String email = oauth2User.getEmail();
        String firstName = oauth2User.getAttribute("family_name");
        String lastName = oauth2User.getAttribute("given_name");

        User authedUser = userService.findUserByEmail(email);
        if(authedUser == null){
            // register new user
            userService.createNewUserAfterOAuthLoginSuccess(firstName,lastName , email );
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
