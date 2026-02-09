package com.scrumsys.policy.entity;

import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class PolicyApproval extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long policyId;
    private Long makerId;
    private Long checkerId;
    private Long approverId;

    private String status;
    private String comments;
}

