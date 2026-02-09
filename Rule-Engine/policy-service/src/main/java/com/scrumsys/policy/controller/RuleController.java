//package com.scrumsys.policy.controller;
//
//@RestController
//@RequestMapping("/api/rules")
//@RequiredArgsConstructor
//public class RuleController {
//
//    private final RuleService ruleService;
//
//    @PostMapping
//    public Rule create(@RequestBody Rule r) {
//        return ruleService.create(r);
//    }
//
//    @GetMapping
//    public List<Rule> list() {
//        return ruleService.all();
//    }
//
//    @GetMapping("/{id}")
//    public Rule get(@PathVariable Long id) {
//        return ruleService.get(id);
//    }
//
//    @PutMapping("/{id}")
//    public Rule update(@PathVariable Long id, @RequestBody Rule r) {
//        return ruleService.update(id, r);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        ruleService.delete(id);
//    }
//}

