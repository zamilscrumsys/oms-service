package com.scrumsys.userservice.entity;


import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "functions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Function extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "api_endpoint")
    private String apiEndpoint;

    @Column(name = "http_method")
    private String httpMethod;
}