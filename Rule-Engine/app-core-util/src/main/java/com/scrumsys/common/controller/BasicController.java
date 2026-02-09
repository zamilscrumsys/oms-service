package com.scrumsys.common.controller;

import com.scrumsys.common.constants.ActionMode;
import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.dto.BasicDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;


public abstract class BasicController {

    public void fillDefaultValue(BasicDTO dto, ActionMode action) {
        switch (action) {
            case CREATE -> {
                dto.setActionMode(action);
                dto.setCreatedBy("SYSTEM");
                dto.setCreationDate(new Date());
                dto.setRcdStatus(AppConstants.ACTIVE_STATUS);
                dto.setReasonCode("NONE");
                dto.setVersionNum(AppConstants.VERSION_ONE);
            }
            case MODIFY -> {
                dto.setActionMode(action);
                dto.setModifiedBy("SYSTEM");
                dto.setModificationDate(new Date());
                dto.setRcdStatus(AppConstants.ACTIVE_STATUS);
                dto.setReasonCode("NONE");
                dto.setVersionNum(dto.getVersionNum() + AppConstants.VERSION_ONE);
            }
            case DELETE -> {
                dto.setActionMode(action);
                dto.setModifiedBy("SYSTEM");
                dto.setModificationDate(new Date());
                dto.setRcdStatus(AppConstants.DELETED_STATUS);
                dto.setReasonCode("NONE");
                dto.setVersionNum(dto.getVersionNum() + AppConstants.VERSION_ONE);
            }
            default -> {
                // no-op
            }
        }
    }

    /**
     * Keeps your old behavior; still works in Spring 6.
     * (Alternative: accept HttpSession/HttpServletRequest as controller method params.)
     */
    protected HttpSession getSession() {
        ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }
}