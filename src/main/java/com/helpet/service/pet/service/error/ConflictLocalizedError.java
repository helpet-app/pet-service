package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    PET_CATEGORY_ALREADY_EXISTS,
    SPECIES_ALREADY_EXISTS,
    SPECIES_DOES_NOT_BELONG_TO_PET_CATEGORY;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
