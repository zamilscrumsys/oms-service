package com.scrumsys.policy.entity;

import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class PolicyEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    private java.util.List<RuleEntity> rules;

    private String status;
}
