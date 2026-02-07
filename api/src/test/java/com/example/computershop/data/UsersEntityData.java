package com.example.computershop.data;

import com.example.computershop.model.entity.UsersEntity;

public class UsersEntityData {

    public static final String USER1_USERNAME = "Omen";
    public static final String USER1_PASSWORD = "0000";

    public static UsersEntity createUser1() {
        UsersEntity user = new UsersEntity();
        user.setUsername(USER1_USERNAME);
        user.setPassword(USER1_PASSWORD);
        return user;
    }
}