package com.scrumsys.policy.service.serviceImpl;

import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.constants.VariableDataType;
import com.scrumsys.common.constants.VariableSource;
import com.scrumsys.common.constants.VariableStatus;
import com.scrumsys.common.exception.ApplicationException;
import com.scrumsys.policy.dto.requestdto.CreateVariableRequestDTO;
import com.scrumsys.policy.dto.requestdto.UpdateVariableRequestDTO;
import com.scrumsys.policy.dto.responsedto.VariableResponseDTO;
import com.scrumsys.policy.entity.VariableEntity;
import com.scrumsys.policy.repository.VariableRepository;
import com.scrumsys.policy.service.VariableService;
//import jakarta.transaction.Transactional;
import com.scrumsys.policy.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VariableServiceImpl implements VariableService {

    private final VariableRepository repository;
    private final Helper helper;

    @Override
    public VariableResponseDTO createVariable(CreateVariableRequestDTO request) {
        //validateCreateRequest(request);

        if (repository.existsByVariableNameAndRcdStatus(
                request.getVariableName(), AppConstants.ACTIVE_STATUS)) {
            throw new ApplicationException.ConflictException(
                    "Variable name already exists");
        }
        if (request.getSource() == VariableSource.REQUEST &&
                request.getDefaultValue() != null) {
            throw new ApplicationException.ValidationException(
                    "Default value not allowed for REQUEST variables");
        }

        if (request.getSource() == VariableSource.RESPONSE &&
                request.getDefaultValue() == null) {
            throw new ApplicationException.ValidationException(
                    "Default value required for RESPONSE variables");
        }

        String normalizedValue = helper.validateAndConvertDefaultValue(
                request.getDataType(),
                request.getDefaultValue()
        );



        VariableEntity entity = VariableEntity.builder()
                .variableName(request.getVariableName())
                .description(request.getDescription())
                .source(request.getSource())
                .dataType(request.getDataType())
                .defaultValue(normalizedValue)
                .status(request.getStatus())
                .build();



        entity.setRcdStatus(AppConstants.ACTIVE_STATUS);
        //NEW LINE FOR STATUS
        entity.setStatus(VariableStatus.valueOf("ACTIVE"));

        return mapToResponse(repository.save(entity));
    }

    @Override
    public VariableResponseDTO updateVariable(Long id, UpdateVariableRequestDTO request) {

        VariableEntity entity = repository
                .findByIdAndRcdStatus(id, AppConstants.ACTIVE_STATUS)
                .orElseThrow(() ->
                        new ApplicationException.NotFoundException("Variable not found"));

        //validateUpdateRequest(request);

        if (request.getSource() == VariableSource.REQUEST &&
                request.getDefaultValue() != null) {
            throw new ApplicationException.ValidationException(
                    "Default value not allowed for REQUEST variables");
        }
        if (request.getSource() == VariableSource.RESPONSE &&
                request.getDefaultValue() == null) {
            throw new ApplicationException.ValidationException(
                    "Default value required for RESPONSE variables");
        }

        String normalizedValue = helper.validateAndConvertDefaultValue(
                request.getDataType(),
                request.getDefaultValue()
        );

        int version= entity.getVersionNum();

        entity.setDescription(request.getDescription());
        entity.setSource(request.getSource());
        entity.setDataType(request.getDataType());
        entity.setDefaultValue(normalizedValue);
        entity.setStatus(request.getStatus());


        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VariableResponseDTO getVariable(Long id) {
        VariableEntity entity = repository
                .findByIdAndRcdStatus(id, AppConstants.ACTIVE_STATUS)
                .orElseThrow(() ->
                        new ApplicationException.NotFoundException("Variable not found"));
        return mapToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VariableResponseDTO> getAllVariables() {
        return repository.findAllByRcdStatus(AppConstants.ACTIVE_STATUS)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteVariable(Long id) {
        VariableEntity entity = repository
                .findByIdAndRcdStatus(id, AppConstants.ACTIVE_STATUS)
                .orElseThrow(() ->
                        new ApplicationException.NotFoundException("Variable not found"));

        entity.setRcdStatus(AppConstants.DELETED_STATUS);
        entity.setStatus(VariableStatus.INACTIVE);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VariableResponseDTO> getVariablesByDataType(
            VariableDataType dataType) {

        return repository
                .findByDataTypeAndRcdStatus(
                        dataType,
                        AppConstants.ACTIVE_STATUS)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ----------------- PRIVATE HELPERS -----------------

//    private void validateCreateRequest(CreateVariableRequestDTO request) {
//        if (request.getSource() == VariableSource.REQUEST &&
//                request.getDefaultValue() != null) {
//            throw new ApplicationException.ValidationException(
//                    "Default value not allowed for REQUEST variables");
//        }
//        if (request.getSource() == VariableSource.RESPONSE &&
//                request.getDefaultValue() == null) {
//            throw new ApplicationException.ValidationException(
//                    "Default value required for RESPONSE variables");
//        }
//    }

    private void validateUpdateRequest(UpdateVariableRequestDTO request) {
        if (request.getSource() == VariableSource.REQUEST &&
                request.getDefaultValue() != null) {
            throw new ApplicationException.ValidationException(
                    "Default value not allowed for REQUEST variables");
        }
        if (request.getSource() == VariableSource.RESPONSE &&
                request.getDefaultValue() == null) {
            throw new ApplicationException.ValidationException(
                    "Default value required for RESPONSE variables");
        }
    }

    private VariableResponseDTO mapToResponse(VariableEntity entity) {
        VariableResponseDTO dto = new VariableResponseDTO();
        dto.setId(entity.getId());
        dto.setVariableName(entity.getVariableName());
        dto.setDescription(entity.getDescription());
        dto.setSource(entity.getSource());
        dto.setDataType(entity.getDataType());
        dto.setDefaultValue(entity.getDefaultValue());
        dto.setStatus(entity.getStatus());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreationDate(entity.getCreationDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModificationDate(entity.getModificationDate());
        dto.setRcdStatus(entity.getRcdStatus());
        return dto;
    }
}

