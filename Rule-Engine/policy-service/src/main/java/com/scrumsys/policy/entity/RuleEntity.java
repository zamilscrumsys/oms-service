package com.scrumsys.policy.entity;

import jakarta.persistence.*;
import lombok.*;
import com.scrumsys.common.entity.BasicEntity;


@Entity
@Data
public class RuleEntity extends BasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String condition;

    @Column(columnDefinition = "TEXT")
    private String action;

    private String status;
}
