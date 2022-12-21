package com.helpet.service.pet.web.util;

import com.helpet.web.handler.LocalizedExceptionHandler;
import com.helpet.web.util.Localizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler extends LocalizedExceptionHandler {
    @Autowired
    public BaseExceptionHandler(Localizer localizer) {
        super(localizer);
    }
}
