package org.javaweb.vuls.utils;

import org.javaweb.net.HttpResponse;
import org.javaweb.net.HttpURLRequest;
import org.javaweb.utils.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

import static org.javaweb.vuls.utils.RASPAPITestUtils.assertResult;

public class RASPModulesTestUtils {

	private static final String SITE_URL = "http://localhost:9060/";

	private static final String MODULES = "scanner,patch,xxe,webshell,inject,filesystem,cmd,ssrf,url,upload,deserialization,expression,xss,speed,spring,base64,fastjson,jackson,xstream,json,jni";

	/**
	 * 禁用某个安全模块
	 *
	 * @return
	 */
	public static void disableModule(String... modules) throws Exception {
		Set<String> set = new HashSet<String>(Arrays.asList(MODULES.split(",")));

		for (String module : modules) {
			set.remove(module);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setModule");
		data.put("module", StringUtils.join(set, ","));

		assertResult(RASPAPITestUtils.request(data));
	}

	/**
	 * 启用所有的安全模块
	 *
	 * @throws Exception
	 */
	public static void enableAllModule() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setModule");
		data.put("module", MODULES);

		assertResult(RASPAPITestUtils.request(data));
	}

	/**
	 * 请求测试用例的安全模块接口并检测是否包含制定的结果
	 *
	 * @param uri
	 * @param data
	 * @param regexp
	 * @return
	 * @throws Exception
	 */
	public static boolean requestAndMatcher(String uri, Map<String, String> data, String regexp) throws Exception {
		HttpResponse response = new HttpURLRequest(SITE_URL + uri).data(data).timeout(30 * 1000).request();

		if (response != null && response.getException() != null) {
			throw response.getException();
		}

		String content = response.body();

		return Pattern.compile(regexp, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
				.matcher(content).find();
	}

}
