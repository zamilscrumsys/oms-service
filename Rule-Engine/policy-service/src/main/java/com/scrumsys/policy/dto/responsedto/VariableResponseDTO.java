package com.scrumsys.policy.dto.responsedto;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.constants.VariableSource;
import com.scrumsys.common.constants.VariableStatus;
import com.scrumsys.common.dto.BasicDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariableResponseDTO extends BasicDTO {

    private Long id;
    private String variableName;
    private String description;
    private VariableSource source;
    private VariableDataType dataType;
    private String defaultValue;
    private VariableStatus status;
}

