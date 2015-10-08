package org.ametiste.shared.specification.domain;

public interface Specification<T> {

	boolean isSatisfiedBy(T object);

}
