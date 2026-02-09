//package com.scrumsys.policy.service;
//
//@Service
//@RequiredArgsConstructor
//public class PolicyService {
//
//    private final PolicyRepository policyRepository;
//    private final PolicyApprovalRepository policyApprovalRepository;
//    private final RuleRepository ruleRepository;
//    private final UserClient userClient;
//
//    public Policy create(PolicyRequest policyRequest, Long userId) {
//        Policy policy = new Policy();
//        policy.setName(policyRequest.getName());
//        policy.setDescription(policyRequest.getDescription());
//        policy.setRules(ruleRepository.findAllById(policyRequest.getRuleIds()));
//        policy.setStatus("DRAFT");
//        policy = policyRepository.save(policy);
//
//        PolicyApproval a = new PolicyApproval();
//        a.setPolicyId(policy.getId());
//        a.setMakerId(userId);
//        a.setStatus("DRAFT");
//
//        policyApprovalRepository.save(a);
//        return policy;
//    }
//
//    public Policy submit(Long id, Long makerId, String comments) {
//        PolicyApproval policyApproval = policyApprovalRepository.findByPolicyId(id).orElseThrow();
//        policyApproval.setStatus("SUBMITTED");
//        policyApproval.setComments(comments);
//        policyApprovalRepository.save(policyApproval);
//
//        Policy p = policyRepository.findById(id).orElseThrow();
//        p.setStatus("SUBMITTED");
//        return policyRepository.save(p);
//    }
//
//    public Policy review(Long id, Long checkerId, String comments) {
//        PolicyApproval a = policyApprovalRepository.findByPolicyId(id).orElseThrow();
//        a.setCheckerId(checkerId);
//        a.setStatus("REVIEWED");
//        a.setComments(comments);
//        policyApprovalRepository.save(a);
//
//        Policy p = policyRepository.findById(id).orElseThrow();
//        p.setStatus("REVIEWED");
//        return policyRepository.save(p);
//    }
//
//    public Policy approve(Long id, Long approverId, String comments) {
//        PolicyApproval a = policyApprovalRepository.findByPolicyId(id).orElseThrow();
//        a.setApproverId(approverId);
//        a.setStatus("APPROVED");
//        a.setComments(comments);
//        policyApprovalRepository.save(a);
//
//        Policy p = policyRepository.findById(id).orElseThrow();
//        p.setStatus("APPROVED");
//        return policyRepository.save(p);
//    }
//
//    public Policy reject(Long id, Long approverId, String comments) {
//        PolicyApproval a = policyApprovalRepository.findByPolicyId(id).orElseThrow();
//        a.setApproverId(approverId);
//        a.setStatus("REJECTED");
//        a.setComments(comments);
//        policyApprovalRepository.save(a);
//
//        Policy p = policyRepository.findById(id).orElseThrow();
//        p.setStatus("REJECTED");
//        return policyRepository.save(p);
//    }
//}

