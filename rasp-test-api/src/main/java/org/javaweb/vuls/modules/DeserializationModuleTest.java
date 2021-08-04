package org.javaweb.vuls.modules;

import org.junit.Assert;
import org.junit.Test;

import static org.javaweb.vuls.utils.RASPModulesTestUtils.*;

/**
 * @author yz
 */
public class DeserializationModuleTest {

	@Test
	public void deserialization() throws Exception {
		// 禁用sql注入检测模块
		disableModule("patch,xss,xxe,webshell,inject,filesystem,cmd,ssrf,url,reflect,upload,deserialization,expression".split(","));

		Assert.assertTrue(requestAndMatcher("modules/deserialization.jsp", null, "BadAttributeValueException"));

		// 开启所有安全模块
		enableAllModule();

		Assert.assertTrue(requestAndMatcher("modules/deserialization.jsp", null, "Access Denied"));
	}

}
