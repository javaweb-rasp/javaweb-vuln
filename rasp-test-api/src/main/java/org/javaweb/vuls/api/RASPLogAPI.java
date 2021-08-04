package org.javaweb.vuls.api;

import org.javaweb.vuls.utils.RASPAPITestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RASPLogAPI {

	@Test
	public void fetch() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Log");
		data.put("action", "fetch");
		data.put("log_type", "attack");

		Assert.assertNotNull(RASPAPITestUtils.request(data));
	}

	@Test
	public void errorLog() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Log");
		data.put("action", "agent_log");

		Assert.assertNotNull(RASPAPITestUtils.request(data));
	}

}