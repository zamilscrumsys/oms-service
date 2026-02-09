package com.scrumsys.policy.repository;

import com.scrumsys.policy.entity.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<ParameterEntity, Long> {
}