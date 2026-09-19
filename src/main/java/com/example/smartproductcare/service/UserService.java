package com.example.smartproductcare.service;

import java.util.Optional;

import com.example.smartproductcare.entity.User;

public interface UserService {

    User registerUser(User user);

    Optional<User> loginUser(String email, String password);

    boolean emailExists(String email);

}