package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.SortDirection;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class OrderCriteriaQueryRepositoryImpl implements OrderCriteriaQueryRepository {

    private final EntityManager em;


    @Override
    public List<Order> findOrdersBySearchRequest(OrderSearchRequestDTO orderSearchRequestDTO) {

        int page = orderSearchRequestDTO.getPage();
        int size = orderSearchRequestDTO.getSize();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);

        orderRoot.join("address", JoinType.LEFT);

        List<Predicate> predicates = buildPredicates(orderSearchRequestDTO, criteriaBuilder, orderRoot);
        query.where(predicates.toArray(new Predicate[0]));

        List<jakarta.persistence.criteria.Order> sortOrders = buildSortOrders(orderSearchRequestDTO, criteriaBuilder, orderRoot);
        query.orderBy(sortOrders);

        TypedQuery<Order> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }


    private List<Predicate> buildPredicates(OrderSearchRequestDTO searchDTO, CriteriaBuilder cb, Root<Order> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchDTO.getFilters().isEmpty()){
            return predicates;
        }

        Map<String, String> filters = searchDTO.getFilters();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getKey();

            if (!value.isBlank()) {
                predicates.add(cb.equal(root.get(fieldName), value));
            }
        }

        return predicates;
    }

    private List<jakarta.persistence.criteria.Order> buildSortOrders(OrderSearchRequestDTO searchDTO, CriteriaBuilder cb, Root<Order> root) {
        List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
        if (searchDTO.getSorts().isEmpty()){
            return orders;
        }

        Map<String, SortDirection> sorts = searchDTO.getSorts();

        for (Map.Entry<String, SortDirection> entry : sorts.entrySet()) {
            String field = entry.getKey();
            SortDirection direction = entry.getValue();

            if (SortDirection.ASC.equals(direction)) {
                orders.add(cb.asc(root.get(field)));
            } else if (SortDirection.DESC.equals(direction)) {
                orders.add(cb.desc(root.get(field)));
            }
        }

        return orders;
    }
}
