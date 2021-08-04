package org.javaweb.vuls.modules;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.javaweb.vuls.utils.RASPModulesTestUtils.*;

public class ProcessBuilderModuleTest {

	@Test
	public void ProcessBuilder() throws Exception {
		//禁用cmd、reflect模块
		disableModule("cmd", "reflect");

		Map<String, String> data = new HashMap<String, String>();
		data.put("cmd", "echo RASP-TEST");
		String[] urls = new String[]{
				"modules/cmd.jsp",
				"modules/cmd.jspx",
		};

		if (getOSName().equals("UNIX")) {
			assertTrue(requestAndMatcher("modules/linux-cmd.jsp",
					data, "RASP-TEST"));
		}

		for (String url : urls) {
			assertTrue(requestAndMatcher(url, data, "RASP-TEST"));
		}

		// 开启所有安全模块
		enableAllModule();

		for (String url : urls) {
			assertTrue(requestAndMatcher(url, data, "Access Denied"));
		}

		if (getOSName().equals("UNIX")) {
			assertTrue(requestAndMatcher("modules/linux-cmd.jsp", data, "Access Denied"));
		}
	}

	/**
	 * 获取系统类型
	 *
	 * @return
	 */
	public String getOSName() {
		String osName = System.getProperty("os.name");

		if (osName.toLowerCase().startsWith("windows")) {
			return "WINDOWS";
		}

		return "UNIX";
	}

}
