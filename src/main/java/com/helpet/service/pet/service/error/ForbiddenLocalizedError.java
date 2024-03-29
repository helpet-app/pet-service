package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ForbiddenLocalizedError implements DefaultEnumLocalizedError {
    ONLY_FAMILY_OWNER_CAN_ADD_MEMBER,
    ONLY_FAMILY_OWNER_CAN_REMOVE_MEMBER;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.forbidden";
    }
}
