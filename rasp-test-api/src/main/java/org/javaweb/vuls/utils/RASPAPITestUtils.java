package org.javaweb.vuls.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.javaweb.net.HttpResponse;
import org.javaweb.net.HttpURLRequest;
import org.javaweb.utils.EncryptUtils;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class RASPAPITestUtils {

	private static final String SITE_URL = "http://localhost:8000/iswaf.api";

	private static final String RC4_KEY = "{rc4_key}";

	private static final String CONNECT_KEY = "{iswaf_connect_key}";

	public static String request(Map<String, Object> map) throws Exception {
		map.put("connectkey", CONNECT_KEY);

		String json = JSON.toJSONString(map);
		String connectData = new String(
				Base64.encodeBase64(EncryptUtils.encryptionRC4Byte(json, RC4_KEY)), "UTF-8"
		);

		Map<String, String> data = new HashMap<String, String>();
		data.put("__LINGXEConnectData__", connectData);
		HttpResponse response = new HttpURLRequest(SITE_URL).timeout(30 * 1000).data(data).request();

		if (response != null && (response.getException() != null || response.getStatusCode() != 200)) {
			System.out.println(new String(response.getBodyBytes()));
			throw response.getException();
		}

		String result = response.body();

		if (result != null || result.contains("__IS_LING_XE_API__")) {
			if (result.contains("__IS_LING_XE_API__")) {
				result = result.substring(0, result.indexOf("__IS_LING_XE_API__"));
			}

			result = EncryptUtils.decryptionRC4(Base64.decodeBase64(result), RC4_KEY);

			System.out.println(result);

			return result;
		}

		return result;
	}

	public static void assertResult(String responseJson) throws Exception {
		Map<String, Object> responseMap = JSON.parseObject(responseJson);

		Assert.assertTrue(responseMap.containsKey("result") && (Boolean) responseMap.get("result"));
	}

}
