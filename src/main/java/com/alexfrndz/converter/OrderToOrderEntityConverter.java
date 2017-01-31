package com.alexfrndz.converter;

import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderEntityConverter implements Converter<Order, OrderEntity> {

    @Override
    public OrderEntity convert(Order source) {
        OrderEntity target = new OrderEntity();
        target.setCustomerName(source.getCustomerName());
        target.setOrderDate(source.getOrderDate());
        return target;
    }


}
