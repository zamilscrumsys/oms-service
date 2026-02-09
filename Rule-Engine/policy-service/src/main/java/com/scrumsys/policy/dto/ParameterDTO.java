package com.scrumsys.policy.dto;

import com.scrumsys.common.dto.BasicDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterDTO extends BasicDTO {

    private Long id;

    private String name;
    private String type;
    private Boolean multipleValues;
}

