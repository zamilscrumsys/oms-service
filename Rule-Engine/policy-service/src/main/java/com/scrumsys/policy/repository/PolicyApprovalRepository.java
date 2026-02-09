package com.scrumsys.policy.repository;

import com.scrumsys.policy.entity.PolicyApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyApprovalRepository extends JpaRepository<PolicyApproval, Long> {
    Optional<PolicyApproval> findByPolicyId(Long policyId);
}
