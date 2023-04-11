package com.helpet.service.pet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    ACCOUNT_DOES_NOT_EXIST,
    PET_DOES_NOT_EXIST,
    USER_DOES_NOT_HAVE_THIS_PET,
    USER_DOES_NOT_HAVE_THIS_FAMILY,
    PET_CATEGORY_DOES_NOT_EXIST,
    SPECIES_DOES_NOT_EXIST,
    PET_DOES_NOT_HAVE_THIS_ANTHROPOMETRY,
    PET_DOES_NOT_HAVE_THIS_DISEASE,
    PET_DOES_NOT_HAVE_THIS_VACCINATION,
    PET_DOES_NOT_HAVE_THIS_FEATURE;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}
