package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CROP")
public class Crop {
    @Id
    @GeneratedValue
    @Column(name = "crop_id")
    private Integer id;

    @Column(name = "CROP_NAME")
    private String cropName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Crop(String cropName) {
        this.cropName = cropName;
    }
}
