package org.javaweb.vuln.utils;

import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {

	/**
	 * Cache for {@link Class#getDeclaredMethods()} plus equivalent default methods
	 * from Java 8 based interfaces, allowing for fast iteration.
	 */
	private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentReferenceHashMap<>(256);

	private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

	public static Method findMethod(Class<?> clazz, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Class<?> type = clazz;

		while (type != null) {
			Method[] methods = type.isInterface() ? type.getMethods() : getDeclaredMethods(type, false);

			for (Method method : methods) {
				if (paramTypes == null || hasSameParams(method, paramTypes)) {
					return method;
				}
			}

			type = type.getSuperclass();
		}
		return null;
	}

	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(name, "Method name must not be null");
		Class<?> type = clazz;

		while (type != null) {
			Method[] methods = type.isInterface() ? type.getMethods() : getDeclaredMethods(type, false);

			for (Method method : methods) {
				if (name.equals(method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes))) {
					return method;
				}
			}

			type = type.getSuperclass();
		}

		return null;
	}

	public static Method[] getDeclaredMethods(Class<?> clazz) {
		return getDeclaredMethods(clazz, true);
	}

	private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
		Assert.notNull(clazz, "Class must not be null");
		Method[] result = declaredMethodsCache.get(clazz);

		if (result == null) {
			try {
				Method[]     declaredMethods = clazz.getDeclaredMethods();
				List<Method> defaultMethods  = findConcreteMethodsOnInterfaces(clazz);

				if (defaultMethods != null) {
					result = new Method[declaredMethods.length + defaultMethods.size()];
					System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
					int index = declaredMethods.length;

					for (Method defaultMethod : defaultMethods) {
						result[index] = defaultMethod;
						index++;
					}
				} else {
					result = declaredMethods;
				}

				declaredMethodsCache.put(clazz, result.length == 0 ? EMPTY_METHOD_ARRAY : result);
			} catch (Throwable ex) {
				throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
						"] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
			}
		}

		return (result.length == 0 || !defensive) ? result : result.clone();
	}

	private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
		List<Method> result = null;

		for (Class<?> ifc : clazz.getInterfaces()) {
			for (Method ifcMethod : ifc.getMethods()) {
				if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
					if (result == null) {
						result = new ArrayList<>();
					}

					result.add(ifcMethod);
				}
			}
		}

		return result;
	}

	private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
		return paramTypes.length == method.getParameterTypes().length &&
				Arrays.equals(paramTypes, method.getParameterTypes());
	}

}
