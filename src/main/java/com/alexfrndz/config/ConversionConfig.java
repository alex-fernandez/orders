package com.alexfrndz.config;

import com.alexfrndz.converter.OrderEntityToOrderConverter;
import com.alexfrndz.converter.OrderLineToOrderLineEntityConverter;
import com.alexfrndz.converter.OrderToOrderEntityConverter;
import com.alexfrndz.converter.utils.ConverterBasedTransformer;
import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.Order;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;


@Configuration
@EnableAutoConfiguration
public class ConversionConfig {

    @Autowired
    private OrderToOrderEntityConverter orderToOrderEntityConverter;

    @Autowired
    private OrderEntityToOrderConverter orderEntityToOrderConverter;

    @Autowired
    private OrderLineToOrderLineEntityConverter orderLineToOrderLineEntityConverter;

    @Bean
    @Qualifier("everyClickConversionService")
    public ConversionService mondelasportsConversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();

        Set<Converter> converters = Sets.newHashSet();
        converters.add(orderEntityToOrderConverter);
        converters.add(orderToOrderEntityConverter);
        converters.add(orderLineToOrderLineEntityConverter);
        factory.setConverters(converters);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean(name = "orderEntityOrderConverterBasedTransformer")
    public ConverterBasedTransformer<OrderEntity, Order> orderEntityOrderConverterBasedTransformer() {
        return new ConverterBasedTransformer<>(Order.class);

    }


}