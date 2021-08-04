package org.javaweb.vuls.modules;

import org.javaweb.vuls.utils.RASPModulesTestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.javaweb.vuls.utils.RASPModulesTestUtils.enableAllModule;
import static org.javaweb.vuls.utils.RASPModulesTestUtils.requestAndMatcher;
import static org.junit.Assert.assertTrue;

/**
 * @author yz
 */
public class SqlInjectModuleTest {

	@Test
	public void mysqlUnionInject() throws Exception {
		// 禁用sql注入检测模块
		RASPModulesTestUtils.disableModule("inject");
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", "1 and 1=2 union select concat('RASP','-Test') from mysql.user ");

		assertTrue(requestAndMatcher("modules/mysql.jsp", data, "RASP-TEST"));

		String[] sqls = new String[]{
				"1 and 1=2 union select 'RASP-TEST' from mysql.user ",
				"1 and 1=2 union select load_file('/etc/passwd') ",
				"1 and 1=2 union select 'test' into outfile '/tmp/1.txt' ",
				"1 and 1=2 union select 'test' into dumpfile '/tmp/1.txt' "
		};

		// 开启所有安全模块
		enableAllModule();

		for (String sql : sqls) {
			data.put("id", sql);

			assertTrue(requestAndMatcher("modules/mysql.jsp", data, "Access Denied"));
		}
	}

	@Test
	public void oracleUnionInject() {

	}

	@Test
	public void mssqlUnionInject() {

	}

}
