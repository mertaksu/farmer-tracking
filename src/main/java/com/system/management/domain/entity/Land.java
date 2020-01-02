package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
