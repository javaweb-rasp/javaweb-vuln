package org.javaweb.vuls.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.javaweb.vuls.utils.RASPAPITestUtils.assertResult;
import static org.javaweb.vuls.utils.RASPAPITestUtils.request;

public class RASPIndexAPI {

	@Test
	public void systemConfig() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "system_config");
		data.put("key", "log.level");
		data.put("value", "DEBUG");

		assertResult(request(data));
	}

	@Test
	public void rulesConfig() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "rulesConfig");
		data.put("key", "expression.SpEL");
		data.put("value", "\\b(Runtime|ProcessBuilder|ClassLoader)|java\\.net\\.(URL|Socket|[a-zA-Z]+URLConnection)|javax\\.script\\.|ognl\\.(OgnlContext|TypeConverter)|\\.(getRealPath|invoke|File|forName|FileUtils|IOUtils|HttpClient)\\b");

		assertResult(request(data));
	}

	@Test
	public void serviceSet() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "service_set");
		data.put("inject", "off");
		data.put("xss", "on");
		data.put("cmd", "off");

		assertResult(request(data));
	}

	@Test
	public void connectStatus() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "connectStatus");
		data.put("type", "info");

		assertResult(request(data));
	}

	@Test
	public void setModule() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setModule");
		data.put("module", "xss,scanner,patch,xxe,webshell,inject,filesystem,cmd,ssrf,url,upload,expression");

		assertResult(request(data));
	}

	@Test
	public void setWhiteList() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setWhiteList");

		List<Map<String, String>> whitelists   = new ArrayList<Map<String, String>>();
		Map<String, String>       whitelistMap = new HashMap<String, String>();

		whitelistMap.put("whitelist_id", "386e8f692eff4aa0bdf35ceeeee");
		whitelistMap.put("service", "*");
		whitelistMap.put("domain", "localhost");
		whitelistMap.put("port", "8089");
		whitelistMap.put("request_uri", "/req.jsp");
		whitelistMap.put("parameters", "['id']");
		whitelists.add(whitelistMap);

		data.put("whitelist", whitelists);
		assertResult(request(data));
	}

	@Test
	public void delWhiteList() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "delWhiteList");
		data.put("whitelist_id", "386e8f692eff4aa0bdf35ceeeee");

		assertResult(request(data));
	}

	@Test
	public void setMode() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setMode");
		data.put("mode", "on");

		assertResult(request(data));
	}

	@Test
	public void getFileInfo() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "getFileInfo");
		data.put("filename", "/etc/passwd");

		assertResult(request(data));
	}

	@Test
	public void getDirFile() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "getDirFile");
		data.put("dir", "/etc/");

		assertResult(request(data));
	}

	@Test
	public void setPatch() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setpatch");

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("hotfixId", "8a8ae38276f0ec6b0176f10020b1007b");
		dataMap.put("scriptName", "/modules/cmd/cmd.jsp");
		dataMap.put("trigger", "[{'triggerIndex':1,'key':'cmd','keyValMethod':'equal','val':'pwd'}]");
		dataMap.put("content", "[{'contentIndex':1,'content_funArgs':[{'before':'pwd','after':'whoami'}],'param':'cmd','functionName':'str_replace'}]");

		data.put("data", dataMap);

		assertResult(request(data));
	}

	@Test
	public void delPatch() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "delpatch");
		data.put("hotfixId", "8a8ae38276f0ec6b0176f10020b1007b");

		assertResult(request(data));
	}

	@Test
	public void setScanner() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setScanner");

		List<Map<String, String>> scannerList = new ArrayList<Map<String, String>>();
		Map<String, String>       scannerMap  = new HashMap<String, String>();

		scannerMap.put("name", "SQLMap");
		scannerMap.put("rules", "sqlmap|xxx");
		scannerList.add(scannerMap);

		data.put("scanner", scannerList);
		assertResult(request(data));
	}

	@Test
	public void setIPBlacklist() throws Exception {
		String[]            ipArray = new String[]{"127.0.0.1", "10.10.99.2"};
		Map<String, Object> data    = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setIPBlacklist");
		data.put("ip_blacklist", ipArray);

		assertResult(request(data));
	}

	@Test
	public void delIPBlacklist() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "delIPBlacklist");
		data.put("ip", "127.0.0.1");

		assertResult(request(data));
	}

	@Test
	public void setURLBlacklist() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("controller", "Index");
		data.put("action", "setURLBlacklist");
		data.put("url_blacklist", "/\\.svn$");

		assertResult(request(data));
	}

}