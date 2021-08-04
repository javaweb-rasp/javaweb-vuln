package org.javaweb.vuls.modules;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.javaweb.vuls.utils.RASPModulesTestUtils.*;
import static org.junit.Assert.assertTrue;

/**
 * @author yz
 */
public class XXEModuleTest {

	@Test
	public void xxe() throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("str", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE test [<!ELEMENT test ANY ><!ENTITY xxe SYSTEM \"file:///etc/passwd\" >]><root><name>&xxe;</name></root>");

		// 禁用Java反射检测模块
		disableModule("xxe");

		assertTrue(requestAndMatcher("modules/xxe.jsp", data, "/bin/sh"));

		// 开启所有安全模块
		enableAllModule();
		assertTrue(requestAndMatcher("modules/xxe.jsp", data, "Access Denied"));
	}

}
