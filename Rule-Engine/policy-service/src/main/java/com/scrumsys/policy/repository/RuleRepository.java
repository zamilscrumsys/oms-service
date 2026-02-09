package com.scrumsys.policy.repository;

import com.scrumsys.policy.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {

}