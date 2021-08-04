package org.javaweb.vuls.modules;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.javaweb.vuls.utils.RASPModulesTestUtils.requestAndMatcher;

public class ExpressionModuleTest {

	@Test
	public void MVEL() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("exp", "123;new java.lang.Runtime.getRuntime().exec('whoami').getInputStream();");

		Assert.assertTrue(requestAndMatcher("modules/mvel.jsp", data, "Access Denied"));
	}
}
