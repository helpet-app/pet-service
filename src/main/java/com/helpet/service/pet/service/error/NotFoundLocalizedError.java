package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    USER_DOES_NOT_EXIST,
    PET_DOES_NOT_EXIST,
    FAMILY_DOES_NOT_EXIST,
    PET_CATEGORY_DOES_NOT_EXIST,
    SPECIES_DOES_NOT_EXIST,
    ANTHROPOMETRY_DOES_NOT_EXIST,
    DISEASE_DOES_NOT_EXIST,
    VACCINATION_DOES_NOT_EXIST,
    PET_FEATURE_DOES_NOT_EXIST;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}
