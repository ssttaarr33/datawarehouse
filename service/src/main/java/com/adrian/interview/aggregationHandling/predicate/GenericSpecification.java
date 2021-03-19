package com.adrian.interview.aggregationHandling.predicate;

import com.adrian.interview.aggregationHandling.value.FilterOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class GenericSpecification<T> implements Specification {
    private static final long serialVersionUID = -3306027032830411569L;

    private List<Criteria> list;

    private Map<FilterOperation, PredicateInterface> PREDICATE_MAP;

    public GenericSpecification() {
        this.list = new ArrayList<>();
        this.PREDICATE_MAP = new HashMap<>();
        this.populatePredicates();
    }

    private void populatePredicates() {
        this.PREDICATE_MAP.put(FilterOperation.GREATER_THAN, (builder, root, criteria) -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.LESS_THAN, (builder, root, criteria) -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.GREATER_THAN_OR_EQUAL, (builder, root, criteria) -> builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.LESS_THAN_OR_EQUAL, (builder, root, criteria) -> builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.NOT_EQUAL, (builder, root, criteria) -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.EQUAL, (builder, root, criteria) -> builder.equal(root.get(criteria.getKey()), criteria.getValue().toString()));
        this.PREDICATE_MAP.put(FilterOperation.MATCH, (builder, root, criteria) -> builder.like(
                builder.lower(root.get(criteria.getKey())),
                "%" + criteria.getValue().toString().toLowerCase() + "%"));
        this.PREDICATE_MAP.put(FilterOperation.MATCH_END, (builder, root, criteria) -> builder.like(
                builder.lower(root.get(criteria.getKey())),
                criteria.getValue().toString().toLowerCase() + "%"));
    }

    public void add(Criteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        list.forEach(criteria -> predicates.add(returnPredicate(builder, root, criteria)));
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate returnPredicate(CriteriaBuilder builder, Root root, Criteria criteria) {
        PredicateInterface predicate = PREDICATE_MAP.get(criteria.getOperation());
        return predicate.create(builder, root, criteria);
    }
}
