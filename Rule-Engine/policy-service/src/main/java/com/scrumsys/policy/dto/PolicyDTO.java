package com.scrumsys.policy.dto;

import com.scrumsys.common.dto.BasicDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyDTO extends BasicDTO {

    private Long id;

    private String name;
    private String description;

    private java.util.List<RuleDTO> rules;

    private String status;
}
