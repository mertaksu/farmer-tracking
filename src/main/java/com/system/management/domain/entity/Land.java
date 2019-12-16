package com.system.management.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "LAND")
public class Land {
    @Id
    @GeneratedValue
    @Column(name = "land_id")
    private Integer id;

    @Column(name = "LAND_NAME")
    private String landName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Land(String name) {this.landName=name;}
}
