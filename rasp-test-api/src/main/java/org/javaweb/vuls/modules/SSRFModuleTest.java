package org.javaweb.vuls.modules;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.javaweb.vuls.utils.RASPModulesTestUtils.*;

public class SSRFModuleTest {

	@Test
	public void ssrf() throws Exception {
		// 禁用Java反射检测模块
		disableModule("ssrf");

		Map<String, String> data = new HashMap<String, String>();
		data.put("url", "http://baidu.com");

		Assert.assertTrue(requestAndMatcher("modules/ssrf.jsp", data, "url=http://www.baidu.com/"));

		enableAllModule();

		Map<String, String> data1 = new HashMap<String, String>();
		data.put("url", "http://xip.io");

		Assert.assertTrue(requestAndMatcher("modules/ssrf.jsp", data1, "Access Denied"));
	}
}
