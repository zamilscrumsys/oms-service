package com.scrumsys.policy.repository;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.policy.entity.VariableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VariableRepository extends JpaRepository<VariableEntity, Long> {

    boolean existsByVariableNameAndRcdStatus(String variableName, String rcdStatus);

    Optional<VariableEntity> findByIdAndRcdStatus(Long id, String rcdStatus);

    List<VariableEntity> findAllByRcdStatus(String rcdStatus);

    List<VariableEntity> findByDataTypeAndRcdStatus(
            VariableDataType dataType,
            String rcdStatus);

}