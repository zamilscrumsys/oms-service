//package com.scrumsys.policy.service;
//
//import com.scrumsys.policy.dto.ParameterDTO;
//import com.scrumsys.policy.dto.RuleDTO;
//import com.scrumsys.policy.entity.ParameterEntity;
//import com.scrumsys.policy.entity.RuleEntity;
//import org.modelmapper.ModelMapper;
//
//@Service
//@RequiredArgsConstructor
//public class ParameterService {

//    private final ParameterRepository parameterRepository;
//    private final ModelMapper modelMapper;
//
//    public ParameterDTO create(ParameterDTO parameterDTO) {
//        ParameterEntity parameterEntity = modelMapper.map(parameterDTO, ParameterEntity.class);
//        return parameterRepository.save(p);
//    }
//
//    public List<ParameterDTO> all() {
//        List<ParameterEntity> parameterEntityList = ruleRepository.findAll();
//        return parameterEntityList.stream()
//                .map(rule -> modelMapper.map(parameterEntity, ParameterDTO.class))
//                .toList();
//        return parameterRepository.findAll();
//    }
//
//    public ParameterDTO update(Long id, ParameterDTO parameterDTO) {
//        ParameterEntity parameterEntity = parameterRepository.findById(id).orElseThrow();
//        parameterEntity.setName(parameterDTO.getName());
//        parameterEntity.setType(parameterDTO.getType());
//        parameterEntity.setMultipleValues(parameterDTO.getMultipleValues());
//        return parameterRepository.save(parameterEntity);
//    }
//
//    public void delete(Long id) {
//        parameterRepository.deleteById(id);
//    }
//}