package com.adrian.interview.model.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateInterface {
    Predicate create(CriteriaBuilder builder, Root root, Criteria criteria);
}
