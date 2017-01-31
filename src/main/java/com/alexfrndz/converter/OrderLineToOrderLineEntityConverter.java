package com.alexfrndz.converter;

import com.alexfrndz.domain.OrderLineEntity;
import com.alexfrndz.pojo.OrderLine;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderLineToOrderLineEntityConverter implements Converter<OrderLine, OrderLineEntity> {

    @Override
    public OrderLineEntity convert(OrderLine source) {
        OrderLineEntity target = new OrderLineEntity();
        target.setQty(source.getQty());
        target.setProduct(source.getProduct());
        target.setPrice(source.getPrice());
        return target;
    }


}
