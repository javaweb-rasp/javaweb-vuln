package org.javaweb.vuln.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/CMD/")
public class CMDController {

	private Map<String, Object> execCMD(String cmd) throws IOException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		if (cmd != null) {
			Process process = new ProcessBuilder(cmd.split("\\s+")).start();
			String  result  = IOUtils.toString(process.getInputStream());
			data.put("result", result);
		} else {
			data.put("msg", "参数不能为空！");
		}

		return data;
	}

	@GetMapping("/get/cmd.do")
	public Map<String, Object> getProcessBuilder(String cmd) throws IOException {
		return execCMD(cmd);
	}

	@PostMapping("/post/cmd.do")
	public Map<String, Object> postProcessBuilder(String cmd) throws IOException {
		return execCMD(cmd);
	}

	@PostMapping("/cookie/cmd.do")
	public Map<String, Object> cookieProcessBuilder(@CookieValue(name = "cmd") String cmd) throws IOException {
		return execCMD(cmd);
	}

	@PostMapping("/header/cmd.do")
	public Map<String, Object> headerProcessBuilder(@RequestHeader(name = "cmd") String cmd) throws IOException {
		return execCMD(cmd);
	}

	@PostMapping(value = "/json/cmd.do", consumes = APPLICATION_JSON_VALUE)
	public Map<String, Object> jsonProcessBuilder(@RequestBody Map<String, Object> map) throws IOException {
		return execCMD((String) map.get("cmd"));
	}

	@PostMapping("/form/cmd.do")
	public Map<String, Object> multipartProcessBuilder(MultipartFile file) throws IOException {
		return execCMD(file.getOriginalFilename());
	}

	@RequestMapping("/unixProcess.do")
	public Map<String, String> unixProcess(String cmd) throws Exception {
		Map<String, String> data = new LinkedHashMap<String, String>();

		if (cmd != null) {
			String[] commands = cmd.split("\\s+");
			Class<?> processClass;

			try {
				processClass = Class.forName("java.lang.UNIXProcess");
			} catch (ClassNotFoundException e) {
				processClass = Class.forName("java.lang.ProcessImpl");
			}

			Constructor<?> constructor = processClass.getDeclaredConstructors()[0];
			constructor.setAccessible(true);
			byte[][] args = new byte[commands.length - 1][];
			int      size = args.length;

			for (int i = 0; i < args.length; i++) {
				args[i] = commands[i + 1].getBytes();
				size += args[i].length;
			}

			byte[] argBlock = new byte[size];
			int    i        = 0;

			for (byte[] arg : args) {
				System.arraycopy(arg, 0, argBlock, i, arg.length);
				i += arg.length + 1;
			}

			byte[] bytes       = commands[0].getBytes();
			byte[] resultBytes = new byte[bytes.length + 1];
			System.arraycopy(bytes, 0, resultBytes, 0, bytes.length);
			resultBytes[resultBytes.length - 1] = (byte) 0;

			Type[]       parameterTypes = constructor.getGenericParameterTypes();
			List<Object> argList        = new ArrayList<Object>();

			Object[] objs = new Object[]{
					resultBytes, argBlock, args.length, null, 1, null, new int[]{-1, -1, -1}, false
			};

			Collections.addAll(argList, objs);

			if (parameterTypes.length == 9) {
				argList.add(false);
			}

			Object object = constructor.newInstance(argList.toArray(new Object[0]));

			Method inMethod = object.getClass().getDeclaredMethod("getInputStream");
			inMethod.setAccessible(true);

			String result = IOUtils.toString((InputStream) inMethod.invoke(object));

			data.put("result", result);
		}

		return data;
	}

}
