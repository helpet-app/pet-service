package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ForbiddenLocalizedError implements DefaultEnumLocalizedError {
    USER_IS_NOT_RELATED_TO_PET,
    USER_IS_NOT_FAMILY_MEMBER;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.forbidden";
    }
}
