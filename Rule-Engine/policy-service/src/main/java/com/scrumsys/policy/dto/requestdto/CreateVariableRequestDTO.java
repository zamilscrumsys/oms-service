package com.scrumsys.policy.dto.requestdto;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.constants.VariableSource;
import com.scrumsys.common.constants.VariableStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVariableRequestDTO {

    private String variableName;
    private String description;
    private VariableSource source;
    private VariableDataType dataType;
  //  private String defaultValue; // validated based on source
    private Object defaultValue;
    private VariableStatus status;
}

