package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "FARMER_DISINFECTION_TRANSACTION")
public class FarmerDisinfectionTransaction {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "PHARMACY_TYPE")
    private String pharmacyType;

    @Column(name = "PHARMACY_DATE")
    private Date pharmacyDate;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    private Boolean status;
}
