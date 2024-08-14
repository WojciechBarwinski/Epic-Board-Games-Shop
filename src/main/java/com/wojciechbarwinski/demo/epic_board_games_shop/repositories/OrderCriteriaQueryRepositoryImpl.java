package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequest;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.SortValue;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Address;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderCriteriaQueryRepositoryImpl implements OrderCriteriaQueryRepository {

    private final EntityManager em;


    @Override
    public List<Order> findOrdersBySearchRequest(OrderSearchRequest orderSearchRequest) {

        int page = orderSearchRequest.getPage();
        int size = orderSearchRequest.getSize();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);

        orderRoot.join("address", JoinType.LEFT);

        List<Predicate> predicates = buildPredicates(orderSearchRequest, criteriaBuilder, orderRoot);
        query.where(predicates.toArray(new Predicate[0]));

        List<jakarta.persistence.criteria.Order> sortOrders = buildSortOrders(orderSearchRequest, criteriaBuilder, orderRoot);
        query.orderBy(sortOrders);

        TypedQuery<Order> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }


    private List<Predicate> buildPredicates(OrderSearchRequest searchDTO, CriteriaBuilder cb, Root<Order> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!searchDTO.getFieldToFilter().isBlank() && !searchDTO.getFieldFilterValue().isBlank()) {
            predicates.add(cb.equal(root.get(searchDTO.getFieldToFilter()), searchDTO.getFieldFilterValue()));
        }

        return predicates;
    }

    private List<jakarta.persistence.criteria.Order> buildSortOrders(OrderSearchRequest searchDTO, CriteriaBuilder cb, Root<Order> root) {
        List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();

        for (SortValue sortValue : searchDTO.getSortValues()) {
            if ("asc".equalsIgnoreCase(sortValue.direction())) {
                orders.add(cb.asc(root.get(sortValue.field())));
            } else if ("desc".equalsIgnoreCase(sortValue.direction())) {
                orders.add(cb.desc(root.get(sortValue.field())));
            }
        }

        return orders;
    }
}
