package com.alexfrndz.repository;

import com.alexfrndz.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long>, OrderRepositoryCustom {


}
