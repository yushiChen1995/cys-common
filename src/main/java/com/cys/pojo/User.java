package com.cys.pojo;

import lombok.Data;

import java.util.Comparator;

/**
 * @author cys
 * @date 2019/6/20
 */
@Data
public class User {
    private String userName;
    private String password;
    private Integer age;

    private Byte gender;

    public User(String userName, String password, Integer age, Byte gender) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }
}
