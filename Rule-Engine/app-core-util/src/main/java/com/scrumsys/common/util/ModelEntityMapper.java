package com.scrumsys.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@RequiredArgsConstructor
public class ModelEntityMapper {

    private final ModelMapper modelMapper;

    public <T> T map(Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        }

        try {
            return modelMapper.map(source, targetType);
        } catch (Exception ex) {
            log.error(
                    "Model mapping failed from {} to {}",
                    source.getClass().getName(),
                    targetType.getName(),
                    ex
            );
            throw new IllegalStateException("Model mapping failed", ex);
        }
    }
}
