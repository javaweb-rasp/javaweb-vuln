package org.javaweb.vuln.commons;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页对象，兼容Spring Data Commons 1.x - 3.x
 *
 * @author yz
 */
public class PageRequestProxy {

	private static final Constructor<?> PAGE_REQUEST_CONSTRUCTOR = findConstructor(
			PageRequest.class, int.class, int.class, Sort.class
	);

	private static final Constructor<?> SORT_CONSTRUCTOR = findConstructor(Sort.class, List.class);

	private static final Sort EMPTY_SORT = sort();

	private static Constructor<?> findConstructor(Class<?> clazz, Class<?>... types) {
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor(types);
			constructor.setAccessible(true);

			return constructor;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Sort sort() {
		return sort(new ArrayList<Sort.Order>());
	}

	public static Sort sort(List<Sort.Order> orders) {
		try {
			if (SORT_CONSTRUCTOR == null) return null;

			return (Sort) SORT_CONSTRUCTOR.newInstance(orders);
		} catch (Exception e) {
			return null;
		}
	}

	public static PageRequest of(Map<String, Object> map) {
		Object pageNumber = map.get("pageNumber");
		Object pageSize   = map.get("pageSize");

		int page = 0;
		int size = 0;

		if (pageNumber != null) {
			page = (Integer) pageNumber - 1;
		}

		if (pageSize != null) {
			size = (Integer) pageSize;
		}

		return of(page, size, null);
	}

	public static PageRequest of(int page, int size) {
		return of(page, size, null);
	}

	public static PageRequest of(int page, int size, Sort sort) {
		try {
			if (PAGE_REQUEST_CONSTRUCTOR == null) return null;

			if (sort == null) {
				sort = EMPTY_SORT;
			}

			return (PageRequest) PAGE_REQUEST_CONSTRUCTOR.newInstance(page, size, sort);
		} catch (Exception e) {
			return null;
		}
	}

}
