package com.scrumsys.policy.service;

import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.policy.dto.requestdto.CreateVariableRequestDTO;
import com.scrumsys.policy.dto.requestdto.UpdateVariableRequestDTO;
import com.scrumsys.policy.dto.responsedto.VariableResponseDTO;

import java.util.List;

public interface VariableService {

    VariableResponseDTO createVariable(CreateVariableRequestDTO request);

    VariableResponseDTO updateVariable(Long id, UpdateVariableRequestDTO request);

    VariableResponseDTO getVariable(Long id);

    List<VariableResponseDTO> getAllVariables();

    void deleteVariable(Long id);

    List<VariableResponseDTO> getVariablesByDataType(
            VariableDataType dataType) ;
}











//existing code
//@Service
//@RequiredArgsConstructor
//public class VariableService {
//
//    private final VariableRepository repo;
//
//    public Variable create(Variable v) {
//        return repo.save(v);
//    }
//
//    public List<Variable> all() {
//        return repo.findAll();
//    }
//
//    public Variable update(Long id, Variable v) {
//        Variable x = repo.findById(id).orElseThrow();
//        x.setName(v.getName());
//        x.setType(v.getType());
//        x.setDefaultValue(v.getDefaultValue());
//        return repo.save(x);
//    }
//
//    public void delete(Long id) {
//        repo.deleteById(id);
//    }
//}

