package org.javaweb.vuls.advice;

import com.alibaba.fastjson.JSON;
import com.github.freva.asciitable.AsciiTable;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.javaweb.utils.HttpServletRequestUtils.getCurrentHttpServletRequest;
import static org.javaweb.utils.HttpServletRequestUtils.getRemoteAddr;

@Aspect
@Order(0)
@Component
public class AuditLogAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(AuditLogAdvice.class);

	@Pointcut("(@annotation(org.springframework.web.bind.annotation.GetMapping) " +
			"|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
			"|| @annotation(org.springframework.web.bind.annotation.RequestMapping) " +
			"|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
			"|| @annotation(org.springframework.web.bind.annotation.PutMapping)) " +
			"|| @annotation(org.springframework.web.bind.annotation.PatchMapping))")
	public void logObject() {
	}

	@Around("logObject()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

		// 当前请求的参数
		Object[] args = pjp.getArgs();

		// 被代理类的对象
		Object target = pjp.getTarget();

		// 方法名称
		String methodName = methodSignature.getName();

		// 方法参数类型
		Class<?>[]         types   = methodSignature.getParameterTypes();
		Method             method  = target.getClass().getMethod(methodName, types);
		HttpServletRequest request = getCurrentHttpServletRequest();

		// 获取函数执行结果
		Object resultObject = pjp.proceed();

		String[] headers = {
				"Method", "URL", "ClassName", "Method", "Result", "Method Args", "Parameter", "Header", "Client IP", "User-Agent"
		};

		String result = "\n" + AsciiTable.getTable(
				headers, new Object[][]{{
						request.getMethod(), getURL(request), target.getClass().getName(),
						method.getName(), getResultStr(resultObject), Arrays.toString(args),
						getParameter(request), getHeaderMap(request).toString(),
						getRemoteAddr(request), getUserAgent(request)
				}}
		);

		System.out.println(result);

		return resultObject;
	}

	private String getResultStr(Object resultObject) {
		if (resultObject != null && !void.class.isAssignableFrom(resultObject.getClass())) {
			return JSON.toJSONString(resultObject);
		}

		return null;
	}

	private StringBuilder getURL(HttpServletRequest request) {
		int    port    = request.getServerPort();
		String context = request.getContextPath();

		return new StringBuilder().append(request.getScheme())
				.append("://").append(request.getServerName())
				.append(port == 80 ? "" : port)
				.append(context != null ? context : "")
				.append(request.getServletPath());
	}

	private String getParameter(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		return parameterMap != null && parameterMap.size() > 0 ? JSON.toJSONString(parameterMap) : "";
	}

	private Map<String, String> getHeaderMap(HttpServletRequest request) {
		Map<String, String> headerMap   = new LinkedHashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String              key       = headerNames.nextElement();
			Enumeration<String> values    = request.getHeaders(key);
			List<String>        valueList = new ArrayList<>();

			while (values.hasMoreElements()) {
				valueList.add(values.nextElement());
			}

			headerMap.put(key, Arrays.toString(valueList.toArray(new String[0])));
		}

		return headerMap;
	}

	private String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");

		if (userAgent == null) return "UNKNOWN";

		UserAgent       ua      = UserAgent.parseUserAgentString(userAgent);
		OperatingSystem system  = ua.getOperatingSystem();
		Browser         browser = ua.getBrowser();
		StringBuilder sb = new StringBuilder()
				.append("Browser: ").append(browser.getName()).append("\n")
				.append("Browser version: ").append(browser.getVersion(userAgent)).append("\n")
				.append("OperatingSystem: ").append(system.getName()).append("\n");

		String  regexp  = "(Baiduspider|Googlebot|Yahoo! Slurp|Sogou|YodaoBot|msnbot)";
		Matcher matcher = Pattern.compile(regexp, CASE_INSENSITIVE).matcher(userAgent);

		if (matcher.find())
			sb.append("Spider: ").append(matcher.group(0)).append("\n");

		return sb.toString();
	}

}
