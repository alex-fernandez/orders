package com.alexfrndz.converter;

import com.alexfrndz.domain.OrderLineEntity;
import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.OrderLine;
import com.alexfrndz.pojo.Order;
import com.google.common.collect.Sets;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderEntityToOrderConverter implements Converter<OrderEntity, Order> {

    @Override
    public Order convert(OrderEntity source) {
        Order target = new Order();
        target.setCustomerName(source.getCustomerName());
        target.setOrderDate(source.getOrderDate());
        target.setId(source.getId());
        Set<OrderLine> orderLines = Sets.newHashSet();
        if (source.getOrderLines() != null) {
            for (OrderLineEntity tempOrderLine : source.getOrderLines()) {
                OrderLine targetPojo = new OrderLine();
                targetPojo.setPrice(tempOrderLine.getPrice());
                targetPojo.setProduct(tempOrderLine.getProduct());
                targetPojo.setQty(tempOrderLine.getQty());
                orderLines.add(targetPojo);
            }
            target.setOrderLines(orderLines);
        }
        return target;
    }


}
