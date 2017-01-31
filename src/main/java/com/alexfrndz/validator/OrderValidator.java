package com.alexfrndz.validator;

import com.alexfrndz.pojo.Order;
import com.alexfrndz.pojo.OrderLine;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {

        Order order = (Order) target;
        if (order.getOrderLines().size() == 0) {
            e.rejectValue("orderLines", "empty", new Object[]{"'orderLines'"}, "orderLines should not be empty.");
        }

        double totalOrder = 0.0;
        for (OrderLine orderLine : order.getOrderLines()) {
            totalOrder += orderLine.getPrice();
        }

        if (Double.compare(totalOrder, Double.valueOf(100.0)) > 0) {
            e.rejectValue("orderLines", "'totalPrice'", new Object[]{"'orderLines'"}, "orderLines total should not exceed 100.");
        }

    }
}
