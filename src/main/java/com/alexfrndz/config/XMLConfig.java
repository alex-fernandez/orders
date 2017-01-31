package com.alexfrndz.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.Map;


@Configuration
@EnableAutoConfiguration
@Slf4j
public class XMLConfig {

    private static final String PACKAGE_TO_SCAN = "com.alexfrndz.pojo";

    @Bean
    @Qualifier("pojoToXMLMarshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        Map<String, Object> props = Maps.newHashMap();
        props.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String[] packagesToScan = {PACKAGE_TO_SCAN};
        marshaller.setPackagesToScan(packagesToScan);
        marshaller.setMarshallerProperties(props);
        return marshaller;
    }


}