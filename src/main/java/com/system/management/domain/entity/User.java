package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @Column(name = "USER_PASS")
    private String userPass;
}
