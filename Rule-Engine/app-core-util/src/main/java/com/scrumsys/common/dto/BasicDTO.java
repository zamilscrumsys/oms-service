package com.scrumsys.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scrumsys.common.constants.ActionMode;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BasicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createdBy;

    private Date creationDate;

    private String modifiedBy;

    private Date modificationDate;

    private String rcdStatus;

    private String reasonCode;

    private int versionNum;

    private ActionMode actionMode;

    private Integer errorCode;

    private String errorMessage;

}