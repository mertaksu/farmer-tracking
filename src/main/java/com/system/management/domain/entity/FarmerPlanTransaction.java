package com.system.management.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "FARMER_PLANNED_TRANSACTION")
public class FarmerPlanTransaction {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "PLAN_TYPE")
    private String planType;

    @Column(name = "PLAN_DATE")
    private Date planDate;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean status;
}
