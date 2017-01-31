package com.alexfrndz.repository;

import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.response.handlers.PaginationSearchRequest;
import com.alexfrndz.pojo.response.handlers.SearchResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryCustom {


    SearchResponse<OrderEntity> search(String query, PaginationSearchRequest searchRequest);


}
