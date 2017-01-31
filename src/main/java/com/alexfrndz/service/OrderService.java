package com.alexfrndz.service;

import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.Order;
import com.alexfrndz.pojo.response.handlers.PaginationSearchRequest;
import com.alexfrndz.pojo.response.handlers.SearchResponse;
import com.alexfrndz.pojo.response.handlers.SuccessMessageResponse;


public interface OrderService {

    SuccessMessageResponse acceptOrder(Order user);

    Order getOrder(long userId);

    SearchResponse<Order> search(String query, PaginationSearchRequest searchRequest);

    OrderEntity createOrder(Order order);
}
