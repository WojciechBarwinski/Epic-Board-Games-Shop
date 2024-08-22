package com.wojciechbarwinski.demo.epic_board_games_shop.repositories;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.OrderSearchRequestDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.SortDirection;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

        predicates.addAll(buildPredicatesForOrderFields("ordererMail", searchDTO.getOrdererMail(), predicates, cb, root));
        predicates.addAll(buildPredicatesForOrderFields("employeeId", searchDTO.getSellerMail(), predicates, cb, root));
        predicates.addAll(buildPredicatesForOrderFields("orderStatus", searchDTO.getStatus(), predicates, cb, root));

        predicates.addAll(buildPredicatesForAddressFields("city", searchDTO.getCity(), predicates, cb, root));
        predicates.addAll(buildPredicatesForAddressFields("phone", searchDTO.getPhone(), predicates, cb, root));
        predicates.addAll(buildPredicatesForAddressFields("street", searchDTO.getStreet(), predicates, cb, root));

        return predicates;
    }


    private List<Predicate> buildPredicatesForOrderFields(String fieldName,
                                                          List<String> fieldValues,
                                                          List<Predicate> predicates,
                                                          CriteriaBuilder cb,
                                                          Root<Order> root) {

        if (fieldValues != null && !fieldValues.isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();

            for (String fieldValue : fieldValues) {
                if (StringUtils.isNotBlank(fieldValue)) {
                    orPredicates.add(cb.equal(root.get(fieldName), fieldValue));
                }
            }

            if (!orPredicates.isEmpty()) {
                predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
            }
        }
        return predicates;
    }

    private List<Predicate> buildPredicatesForAddressFields(String fieldName,
                                                            List<String> fieldValues,
                                                            List<Predicate> predicates,
                                                            CriteriaBuilder cb,
                                                            Root<Order> root) {


        if (fieldValues != null && !fieldValues.isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();

            for (String fieldValue : fieldValues) {
                if (StringUtils.isNotBlank(fieldValue)) {
                    orPredicates.add(cb.like(root.get("address").get(fieldName), "%" + fieldValue + "%"));
                }
            }

            if (!orPredicates.isEmpty()) {
                predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
            }
        }
        return predicates;
    }

    private List<jakarta.persistence.criteria.Order> buildSortOrders(OrderSearchRequestDTO searchDTO, CriteriaBuilder cb, Root<Order> root) {
        List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
        if (searchDTO.getSortField() == null || searchDTO.getSortDirection() == null || searchDTO.getSortField().isBlank()) {
            return orders;
        }
        String field = searchDTO.getSortField();
        SortDirection direction = searchDTO.getSortDirection();

        if (SortDirection.ASC.equals(direction)) {
            orders.add(cb.asc(root.get(field)));
        } else if (SortDirection.DESC.equals(direction)) {
            orders.add(cb.desc(root.get(field)));
        }

        return orders;
    }
}
