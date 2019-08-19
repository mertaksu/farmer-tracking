package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Land(String landName) {
        this.landName = landName;
    }
}
