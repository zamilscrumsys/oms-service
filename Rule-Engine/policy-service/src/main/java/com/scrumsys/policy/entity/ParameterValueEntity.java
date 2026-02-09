package com.scrumsys.policy.entity;

import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ParameterValueEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ParameterEntity parameter;

    private String value;
}
