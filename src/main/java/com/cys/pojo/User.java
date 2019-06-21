package com.cys.pojo;

import lombok.Data;

/**
 * @author cys
 * @date 2019/6/20
 */
@Data
public class User {
    private String userName;
    private String password;
    private Integer age;

    public User(String userName, String password, Integer age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
