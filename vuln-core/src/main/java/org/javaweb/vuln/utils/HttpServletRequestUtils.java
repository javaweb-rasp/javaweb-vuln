package org.javaweb.vuln.utils;

import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

public class HttpServletRequestUtils {

	private static Method getRequestMethod = null;

	private static Method getResponseMethod = null;

	private static Method getSessionMethod = null;

	private static Method setAttributeMethod = null;

	private static Method getRemoteAddr = null;

	private static Method getRequestURL = null;

	public static Object getHttpServletRequest() {
		ServletRequestAttributes attributes      = (ServletRequestAttributes) currentRequestAttributes();
		Class<?>                 attributesClass = attributes.getClass();

		if (getRequestMethod == null) {
			getRequestMethod = findMethod(attributesClass, "getRequest");
		}

		return invokeMethod(getRequestMethod, attributes);
	}

	public static Object getHttpServletResponse() {
		ServletRequestAttributes attributes      = (ServletRequestAttributes) currentRequestAttributes();
		Class<?>                 attributesClass = attributes.getClass();

		if (getResponseMethod == null) {
			getResponseMethod = findMethod(attributesClass, "getResponse");
		}

		return invokeMethod(getResponseMethod, attributes);
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

	public static String getRemoteAddr() {
		Object   request      = getHttpServletRequest();
		Class<?> requestClass = request.getClass();

		if (getRemoteAddr == null) {
			getRemoteAddr = findMethod(requestClass, "getRemoteAddr");
		}

		return (String) invokeMethod(getRemoteAddr, request);
	}

	public static StringBuffer getRequestURL() {
		Object   request      = getHttpServletRequest();
		Class<?> requestClass = request.getClass();

		if (getRequestURL == null) {
			getRequestURL = findMethod(requestClass, "getRequestURL");
		}

		return (StringBuffer) invokeMethod(getRequestURL, request);
	}

}
