package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    PET_CATEGORY_ALREADY_EXISTS,
    SPECIES_ALREADY_EXISTS,
    PET_ALREADY_HAS_FAMILY;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
