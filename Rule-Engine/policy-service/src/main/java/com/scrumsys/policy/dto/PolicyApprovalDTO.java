package com.scrumsys.policy.dto;

import com.scrumsys.common.dto.BasicDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyApprovalDTO extends BasicDTO {

    private Long id;

    private Long policyId;
    private Long makerId;
    private Long checkerId;
    private Long approverId;

    private String status;
    private String comments;
}

