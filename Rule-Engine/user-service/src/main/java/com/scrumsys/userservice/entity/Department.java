package com.scrumsys.userservice.entity;



import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "department_code", unique = true)
    private String departmentCode;
}
