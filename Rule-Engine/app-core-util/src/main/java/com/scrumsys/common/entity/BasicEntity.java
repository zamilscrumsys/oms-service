package com.scrumsys.common.entity;

import com.scrumsys.common.constants.ActionMode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "created_by", updatable = false, length = 100)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private Date creationDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modification_date")
    private Date modificationDate;

    @Column(name = "rcd_status", length = 20)
    private String rcdStatus;

    @Version
    @Column(name = "version_num")
    @Transient
    private int versionNum =0;

    @Transient
    private String reasonCode;

    @Transient
    private ActionMode actionMode;

    @Transient
    private boolean auditFlag;

    @Transient
    private String processId;
}




//package com.scrumsys.common.entity;
//
//import com.scrumsys.common.constants.ActionMode;
//import jakarta.persistence.Column;
//import jakarta.persistence.MappedSuperclass;
//import jakarta.persistence.Transient;
//import jakarta.persistence.Version;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@Getter
//@Setter
//@ToString
//@MappedSuperclass
//public abstract class BasicEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Column(name = "created_by", updatable = false, length = 100)
//    private String createdBy;
//
//    @CreationTimestamp
//    @Column(name = "creation_date", updatable = false, nullable = false)
//    private Date creationDate;
//
//    @Column(name = "modified_by", length = 100)
//    private String modifiedBy;
//
//    @UpdateTimestamp
//    @Column(name = "modification_date")
//    private Date modificationDate;
//
//    @Column(name = "rcd_status", length = 20)
//    private String rcdStatus;
//
//    @Version
//    @Column(name = "version_num")
//    private int versionNum;
//
//    /* =========================
//       Transient / Runtime Fields
//       ========================= */
//
//    @Transient
//    private String reasonCode;
//
//    @Transient
//    private ActionMode actionMode;
//
//    @Transient
//    private boolean auditFlag;
//
//    @Transient
//    private String processId;
//}