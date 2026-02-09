package com.scrumsys.policy.dto;

import com.scrumsys.common.dto.BasicDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterValueDTO extends BasicDTO {


    private Long id;

    private ParameterDTO parameter;

    private String value;
}
