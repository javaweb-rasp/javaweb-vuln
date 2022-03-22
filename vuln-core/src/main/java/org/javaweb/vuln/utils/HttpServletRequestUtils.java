package org.javaweb.vuln.utils;

import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

public class HttpServletRequestUtils extends org.javaweb.utils.HttpServletRequestUtils {

	private static Method getSessionMethod = null;

	private static Method setAttributeMethod = null;

	public static Object getHttpServletRequest() {
		return ((ServletRequestAttributes) currentRequestAttributes()).getRequest();
	}

	public static Object getHttpServletResponse() {
		return ((ServletRequestAttributes) currentRequestAttributes()).getResponse();
	}

	public static Object getHttpSession() {
		Object   request      = getHttpServletRequest();
		Class<?> requestClass = request.getClass();

		if (getSessionMethod == null) {
			getSessionMethod = findMethod(requestClass, "getSession");
		}

		return invokeMethod(getSessionMethod, request);
	}

	public static void setSessionAttribute(String name, Object value) {
		Object session = getHttpSession();

		if (session == null) {
			throw new NullPointerException("无法获取Session对象");
		}

		Class<?> sessionClass = session.getClass();

		if (setAttributeMethod == null) {
			setAttributeMethod = findMethod(sessionClass, "setAttribute", String.class, Object.class);
		}

		invokeMethod(setAttributeMethod, session, name, value);
	}

}
