package com.system.management.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_GSM")
    private String userGsm;

    @Column(name = "USER_PASS")
    private String userPass;
}
