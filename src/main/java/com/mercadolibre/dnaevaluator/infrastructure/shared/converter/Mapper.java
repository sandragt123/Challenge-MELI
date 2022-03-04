package com.mercadolibre.dnaevaluator.infrastructure.shared.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Mapper {

    @Bean
    ModelMapper Mapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setImplicitMappingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setAmbiguityIgnored(true);
        return modelMapper;
    }

}
