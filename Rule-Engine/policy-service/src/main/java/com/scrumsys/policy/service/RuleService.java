//package com.scrumsys.policy.service;
//
//import com.scrumsys.policy.dto.RuleDTO;
//import com.scrumsys.policy.entity.RuleEntity;
//import com.scrumsys.policy.repository.RuleRepository;
//import com.scrumsys.common.config.ModelMapperConfig;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RuleService {
//
//    private final RuleRepository ruleRepository;
//    private final ModelMapper modelMapper;
//
//    public RuleEntity create(RuleDTO ruleDTO) {
//        RuleEntity ruleEntity = modelMapper.map(ruleDTO, RuleEntity.class);
//        return ruleRepository.save(ruleEntity);
//    }
//
//    public List<RuleDTO> all() {
//        List<RuleEntity> ruleEntityList = ruleRepository.findAll();
//        return ruleEntityList.stream()
//                .map(rule -> modelMapper.map(ruleEntity, RuleDTO.class))
//                .toList();
//    }
//
//    public RuleDTO get(Long id) {
//        RuleEntity ruleEntity = ruleRepository.findById(id).orElseThrow();
//        return modelMapper.map(ruleEntity, RuleDTO.class);
//    }
//
//    public RuleDTO update(Long id, RuleDTO ruleDTO) {
//        RuleEntity ruleEntity = get(id);
//        ruleEntity.setName(ruleDTO.getName());
//        ruleEntity.setCondition(ruleDTO.getCondition());
//        ruleEntity.setAction(ruleDTO.getAction());
//        ruleEntity.setStatus(ruleDTO.getStatus());
//        return ruleRepository.save(ruleEntity);
//    }
//
//    public void delete(Long id) {
//        ruleRepository.deleteById(id);
//    }
//}
