package com.alexfrndz.repository;

import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.response.handlers.PaginationSearchRequest;
import com.alexfrndz.pojo.response.handlers.SearchResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepositoryCustom {


    @Autowired
    private EntityManager em;

    @Override
    public SearchResponse<OrderEntity> search(String searchQuery, PaginationSearchRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> query = cb.createQuery(OrderEntity.class);
        List<Predicate> conditions = Lists.newArrayList();

        Root<OrderEntity> userRoot = query.from(OrderEntity.class);

        if (StringUtils.isNotEmpty(searchQuery)) {
            Predicate customerNamePredicate = cb.like(cb.lower(userRoot.<String>get(OrderEntity.Paths.customerName.name())), "%" + searchQuery.toLowerCase() + "%");

            Predicate orderIdPredicate = cb.equal(userRoot.get(OrderEntity.Paths.id.name()), StringUtils.isNumeric(searchQuery) ? Long.parseLong(searchQuery) : 0);
            Predicate searchPredicate = cb.or(customerNamePredicate, orderIdPredicate);
            DBUtils.addPredicateToConditions(conditions, searchPredicate);

        }

        query.where(conditions.toArray(new Predicate[conditions.size()]));
        DBUtils.addSort(cb, query, userRoot, "id");
        query.distinct(true);
        List<OrderEntity> entities = em.createQuery(query)
                .setFirstResult(searchRequest.getStart()).setMaxResults(searchRequest.getCount()).getResultList();
        Long total = 0L;
        //count query
        CriteriaQueryImpl<Long> countQuery = (CriteriaQueryImpl<Long>) cb.createQuery(Long.class);
        countQuery.getRoots().add(userRoot);
        countQuery.select(cb.countDistinct(userRoot));
        countQuery.where(conditions.toArray(new Predicate[conditions.size()]));
        total = em.createQuery(countQuery).getSingleResult();
        return new SearchResponse<OrderEntity>(searchRequest.getStart(), searchRequest.getCount(), total.intValue(), entities);
    }


}