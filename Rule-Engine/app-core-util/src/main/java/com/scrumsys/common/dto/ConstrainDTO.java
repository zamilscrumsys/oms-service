package com.scrumsys.common.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class ConstrainDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;

    private String fieldName;

    private String fieldValue;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}