package com.scrumsys.policy.dto;

import com.scrumsys.common.dto.BasicDTO;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleDTO extends BasicDTO {

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
