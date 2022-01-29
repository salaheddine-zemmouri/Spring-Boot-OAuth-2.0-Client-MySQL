package com.example.demo.Services;

import com.example.demo.Models.User;

public interface UserService {
    User findUserByEmail(String email);
    void createNewUserAfterOAuthLoginSuccess(String firstName, String lastName ,String email);
}
