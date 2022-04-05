package org.javaweb.vuln.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

@RestController
@RequestMapping("/CMD/")
public class CMDController {

	@RequestMapping("/cmd.do")
	public Map<String, String> cmd(String cmd) throws Exception {
		Map<String, String> data = new LinkedHashMap<String, String>();

		if (cmd != null) {
			Process process = new ProcessBuilder(cmd.split("\\s+")).start();
			String  result  = IOUtils.toString(process.getInputStream());
			data.put("result", result);
		} else {
			data.put("msg", "参数不能为空！");
		}

		return data;
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
