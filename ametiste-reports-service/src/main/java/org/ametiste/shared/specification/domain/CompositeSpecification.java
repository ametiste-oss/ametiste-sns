package org.ametiste.shared.specification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class CompositeSpecification<T> implements Specification<T> {

	private final List<Specification<T>> list;

	public CompositeSpecification() {
		list = new ArrayList<Specification<T>>();

	}

	public void addCriteria(Specification<T> criteria) {
		list.add(criteria);
	}

	public List<Specification<T>> getCriterias() {
		return Collections.unmodifiableList(list);
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		for (Specification<T> criteria : list) {
			if (!criteria.isSatisfiedBy(object))
				return false;
		}
		return true;
	}

}
