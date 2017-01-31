package com.alexfrndz.service;


import com.alexfrndz.converter.SearchResponseConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;

@Setter
public abstract class AbstractService {

    @Autowired
    @Qualifier("everyClickConversionService")
    protected ConversionService everyClickConversionService;

    @Autowired
    protected SearchResponseConverter searchResponseConverter;



}
