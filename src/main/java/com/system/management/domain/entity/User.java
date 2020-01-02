package com.system.management.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Land> landList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Crop> cropList;

}
