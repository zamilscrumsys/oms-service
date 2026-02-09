package com.scrumsys.policy.util;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.exception.ApplicationException;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public String validateAndConvertDefaultValue(
            VariableDataType dataType,
            Object value) {

        if (value == null) {
            return null;
        }

        try {
            return switch (dataType) {

                case STRING -> {
                    if (!(value instanceof String)) {
                        throw new ApplicationException.ValidationException(
                                "Expected STRING value");
                    }
                    yield (String) value;
                }

                case NUMBER -> {
                    if (value instanceof Integer || value instanceof Long) {
                        yield String.valueOf(value);
                    }
                    if (value instanceof Double || value instanceof Float) {
                        yield String.valueOf(value);
                    }
                    throw new ApplicationException.ValidationException(
                            "Expected NUMBER value");
                }

                case BOOLEAN -> {
                    if (!(value instanceof Boolean)) {
                        throw new ApplicationException.ValidationException(
                                "Expected BOOLEAN value");
                    }
                    yield String.valueOf(value);
                }
            };
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException.ValidationException(
                    "Invalid default value for dataType " + dataType);
        }
    }

}
