package com.scrumsys.policy.controller;


import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.policy.dto.requestdto.CreateVariableRequestDTO;
import com.scrumsys.policy.dto.requestdto.UpdateVariableRequestDTO;
import com.scrumsys.policy.dto.responsedto.VariableResponseDTO;
import com.scrumsys.policy.service.VariableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/variables")
@RequiredArgsConstructor
public class VariableController {

    private final VariableService service;

    @PostMapping
    public VariableResponseDTO create(@RequestBody CreateVariableRequestDTO request) {
        return service.createVariable(request);
    }

    @PutMapping("/{id}")
    public VariableResponseDTO update(
            @PathVariable Long id,
            @RequestBody UpdateVariableRequestDTO request) {
        return service.updateVariable(id, request);
    }

    @GetMapping("/{id}")
    public VariableResponseDTO get(@PathVariable Long id) {
        return service.getVariable(id);
    }

    @GetMapping
    public List<VariableResponseDTO> getAll() {
        return service.getAllVariables();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.deleteVariable(id);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Variable deleted successfully",
                        "id", id
                )
        );
    }

    @GetMapping("/type/{dataType}")
    public List<VariableResponseDTO> getByType(
            @PathVariable VariableDataType dataType) {
        return service.getVariablesByDataType(dataType);
    }



}


