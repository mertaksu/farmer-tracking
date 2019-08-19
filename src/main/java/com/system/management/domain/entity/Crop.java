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
@Table(name = "CROP")
public class Crop {
    @Id
    @GeneratedValue
    @Column(name = "crop_id")
    private Integer id;

    @Column(name = "CROP_NAME")
    private String cropName;

    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Crop(String cropName) {
        this.cropName = cropName;
    }
}
