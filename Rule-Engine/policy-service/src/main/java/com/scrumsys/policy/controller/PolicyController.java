//package com.scrumsys.user.controller;
//
//@RestController
//@RequestMapping("/api/policies")
//@RequiredArgsConstructor
//public class PolicyController {
//
//    private final PolicyService policyService;
//
//    @PostMapping
//    public Policy create(@RequestBody PolicyRequest req,
//                         @RequestHeader("X-Auth-User") Long userId) {
//        return policyService.create(req, userId);
//    }
//
//    @PostMapping("/{id}/submit")
//    public Policy submit(@PathVariable Long id, @RequestBody ApprovalRequest req) {
//        return policyService.submit(id, req.getUserId(), req.getComments());
//    }
//
//    @PostMapping("/{id}/review")
//    public Policy review(@PathVariable Long id, @RequestBody ApprovalRequest req) {
//        return policyService.review(id, req.getUserId(), req.getComments());
//    }
//
//    @PostMapping("/{id}/approve")
//    public Policy approve(@PathVariable Long id, @RequestBody ApprovalRequest req) {
//        return policyService.approve(id, req.getUserId(), req.getComments());
//    }
//
//    @PostMapping("/{id}/reject")
//    public Policy reject(@PathVariable Long id, @RequestBody ApprovalRequest req) {
//        return policyService.reject(id, req.getUserId(), req.getComments());
//    }
//}
