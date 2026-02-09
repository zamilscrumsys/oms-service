package com.scrumsys.policy.dto.requestdto;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.constants.VariableSource;
import com.scrumsys.common.constants.VariableStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVariableRequestDTO {

    private String description;
    private VariableSource source;
    private VariableDataType dataType;
    private Object defaultValue;
    private VariableStatus status;
}
