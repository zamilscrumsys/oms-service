package com.scrumsys.policy.repository;

import com.scrumsys.policy.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
}