package com.scrumsys.policy.entity;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.constants.VariableSource;
import com.scrumsys.common.constants.VariableStatus;
import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;




import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "mso_variable",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"variable_name", "rcd_status"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariableEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // technical PK

    @Column(name = "variable_name", nullable = false, length = 100)
    private String variableName;   // business unique key

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 20)
    private VariableSource source;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 20)
    private VariableDataType dataType;

    @Column(name = "default_value", length = 255)
    private String defaultValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VariableStatus status;
}






//Existing code
//@Entity
//@Data
//public class VariableEntity extends BasicEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String type;
//    private String defaultValue;
//}
